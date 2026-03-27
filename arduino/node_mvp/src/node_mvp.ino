#include <SPI.h>
#include <MFRC522.h>
#include <Servo.h>

// TODO: добавить сетевые библиотеки для MQTT (WiFi/Ethernet + PubSubClient)

struct PinConfig {
  static const int RFID_SS = 10;
  static const int RFID_RST = 9;
  static const int SERVO_SWITCH_1 = 5;
};

class ReaderAdapter {
public:
  ReaderAdapter(byte ssPin, byte rstPin): rfid(ssPin, rstPin) {}

  void begin() {
    SPI.begin();
    rfid.PCD_Init();
  }

  bool readTag(String &uidOut) {
    if (!rfid.PICC_IsNewCardPresent() || !rfid.PICC_ReadCardSerial()) {
      return false;
    }
    uidOut = "";
    for (byte i = 0; i < rfid.uid.size; i++) {
      uidOut += String(rfid.uid.uidByte[i], HEX);
    }
    uidOut.toUpperCase();
    rfid.PICC_HaltA();
    rfid.PCD_StopCrypto1();
    return true;
  }

private:
  MFRC522 rfid;
};

class ServoSwitch {
public:
  ServoSwitch(int pin): pin(pin) {}

  void begin() { servo.attach(pin); }

  void setStraight() { servo.write(30); }
  void setDiverge()  { servo.write(120); }

private:
  int pin;
  Servo servo;
};

ReaderAdapter reader(PinConfig::RFID_SS, PinConfig::RFID_RST);
ServoSwitch sw1(PinConfig::SERVO_SWITCH_1);

String lastUid = "";
unsigned long lastUidMillis = 0;
const unsigned long DUP_WINDOW_MS = 1500;
unsigned long lastHeartbeat = 0;

void publishHeartbeat() {
  // TODO: MQTT publish brio/v1/nodes/node-1/heartbeat
  Serial.println("[HB] node-1 ONLINE");
}

void publishRfidEvent(const String &uid) {
  // TODO: MQTT publish brio/v1/nodes/node-1/events/rfid.detected
  Serial.print("[RFID] UID=");
  Serial.println(uid);
}

void handleIncomingCommands() {
  // TODO: MQTT subscribe brio/v1/nodes/node-1/commands/switch.set
  // parse JSON and call sw1.setStraight() / sw1.setDiverge()
}

bool isDuplicateRead(const String &uid) {
  unsigned long now = millis();
  if (uid == lastUid && (now - lastUidMillis) < DUP_WINDOW_MS) {
    return true;
  }
  lastUid = uid;
  lastUidMillis = now;
  return false;
}

void setup() {
  Serial.begin(115200);
  reader.begin();
  sw1.begin();
  sw1.setStraight();

  // TODO: init network and MQTT
  Serial.println("Node MVP started");
}

void loop() {
  String uid;
  if (reader.readTag(uid) && !isDuplicateRead(uid)) {
    publishRfidEvent(uid);
  }

  if (millis() - lastHeartbeat > 5000) {
    publishHeartbeat();
    lastHeartbeat = millis();
  }

  handleIncomingCommands();
  delay(20);
}
