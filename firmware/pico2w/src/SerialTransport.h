#pragma once

class SerialTransport {
public:
  void begin(unsigned long baudRate = 115200) {
    Serial.begin(baudRate);
  }

  void publishHeartbeat(const String &nodeId, unsigned long uptimeSec) {
    Serial.print("{\"type\":\"heartbeat\",\"nodeId\":\"");
    Serial.print(nodeId);
    Serial.print("\",\"transport\":\"USB_SERIAL\",\"uptimeSec\":");
    Serial.print(uptimeSec);
    Serial.println("}");
  }

  void publishRfidEvent(const String &nodeId, const String &readerId, const String &uid) {
    Serial.print("{\"type\":\"rfid.detected\",\"nodeId\":\"");
    Serial.print(nodeId);
    Serial.print("\",\"readerId\":\"");
    Serial.print(readerId);
    Serial.print("\",\"tagUid\":\"");
    Serial.print(uid);
    Serial.println("\"}");
  }

  void publishSwitchState(const String &nodeId, const String &switchId, const String &state) {
    Serial.print("{\"type\":\"switch.state\",\"nodeId\":\"");
    Serial.print(nodeId);
    Serial.print("\",\"switchId\":\"");
    Serial.print(switchId);
    Serial.print("\",\"state\":\"");
    Serial.print(state);
    Serial.println("\"}");
  }

  bool readLine(String &lineOut) {
    if (!Serial.available()) {
      return false;
    }

    lineOut = Serial.readStringUntil('\n');
    lineOut.trim();
    return lineOut.length() > 0;
  }
};
