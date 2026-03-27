package com.brio.trainmanager.api;

import com.brio.trainmanager.domain.Node;
import com.brio.trainmanager.domain.Reader;
import com.brio.trainmanager.domain.Switch;

import java.util.List;

public record SystemStateDto(List<Node> nodes, List<Reader> readers, List<Switch> switches, List<Object> recentEvents) {}
