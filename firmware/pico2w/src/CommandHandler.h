#pragma once

struct SwitchCommand {
  String switchId;
  String targetState;
  bool valid = false;
};

class CommandHandler {
public:
  // MVP parser skeleton. In production replace with robust JSON parsing.
  SwitchCommand parse(const String &line) {
    SwitchCommand cmd;

    if (line.indexOf("\"type\":\"switch.set\"") < 0) {
      return cmd;
    }

    if (line.indexOf("\"switchId\":\"sw-1\"") >= 0) {
      cmd.switchId = "sw-1";
    } else if (line.indexOf("\"switchId\":\"sw-2\"") >= 0) {
      cmd.switchId = "sw-2";
    }

    if (line.indexOf("\"targetState\":\"STRAIGHT\"") >= 0) {
      cmd.targetState = "STRAIGHT";
    } else if (line.indexOf("\"targetState\":\"DIVERGE\"") >= 0) {
      cmd.targetState = "DIVERGE";
    }

    cmd.valid = cmd.switchId.length() > 0 && cmd.targetState.length() > 0;
    return cmd;
  }
};
