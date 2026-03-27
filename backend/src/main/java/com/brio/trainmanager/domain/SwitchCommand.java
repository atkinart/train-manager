package com.brio.trainmanager.domain;

import java.time.Instant;

public record SwitchCommand(String commandId, String switchId, SwitchState targetState, String reason, Instant ts) {}
