package com.brio.trainmanager.domain;

import java.time.Instant;

public record RfidEvent(String eventId, String nodeId, String readerId, String tagUid, Instant ts) {}
