#include "Config.h"
#include "ReaderAdapter.h"
#include "ServoSwitch.h"
#include "SerialTransport.h"
#include "CommandHandler.h"

String NODE_ID = "node-1";
String READER_ID = "reader-a";

ReaderAdapter reader(Config::RFID_SS, Config::RFID_RST);
ServoSwitch sw1(Config::SERVO_1_PWM, 30, 120);
ServoSwitch sw2(Config::SERVO_2_PWM, 30, 120);
SerialTransport transport;
CommandHandler commandHandler;

String lastUid = "";
unsigned long lastUidMillis = 0;
unsigned long lastHeartbeat = 0;

bool isDuplicateRead(const String &uid) {
  unsigned long now = millis();
  if (uid == lastUid && (now - lastUidMillis) < Config::DUP_WINDOW_MS) {
    return true;
  }
  lastUid = uid;
  lastUidMillis = now;
  return false;
}

void applyCommand(const SwitchCommand &cmd) {
  if (!cmd.valid) return;

  ServoSwitch *target = nullptr;
  if (cmd.switchId == "sw-1") target = &sw1;
  if (cmd.switchId == "sw-2") target = &sw2;
  if (!target) return;

  if (cmd.targetState == "STRAIGHT") {
    target->setStraight();
  } else if (cmd.targetState == "DIVERGE") {
    target->setDiverge();
  }

  transport.publishSwitchState(NODE_ID, cmd.switchId, target->state());
}

void setup() {
  transport.begin(115200);

  reader.begin();
  sw1.begin();
  sw2.begin();
  sw1.setStraight();
  sw2.setStraight();

  Serial.println("[BOOT] Pico 2 W node MVP started");
}

void loop() {
  String uid;
  if (reader.readTag(uid) && !isDuplicateRead(uid)) {
    transport.publishRfidEvent(NODE_ID, READER_ID, uid);
  }

  if (millis() - lastHeartbeat > Config::HEARTBEAT_MS) {
    transport.publishHeartbeat(NODE_ID, millis() / 1000UL);
    lastHeartbeat = millis();
  }

  String incomingLine;
  if (transport.readLine(incomingLine)) {
    SwitchCommand cmd = commandHandler.parse(incomingLine);
    applyCommand(cmd);
  }

  delay(20);
}
