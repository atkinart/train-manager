package com.brio.trainmanager.store;

import com.brio.trainmanager.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStateStore {
    private final Map<String, Node> nodes = new ConcurrentHashMap<>();
    private final Map<String, Reader> readers = new ConcurrentHashMap<>();
    private final Map<String, Switch> switches = new ConcurrentHashMap<>();
    private final Deque<Object> recentEvents = new ArrayDeque<>();

    public InMemoryStateStore() {
        nodes.put("node-1", new Node("node-1", "UNKNOWN"));
        readers.put("reader-a", new Reader("reader-a", "node-1", "marker-a"));
        switches.put("sw-1", new Switch("sw-1", "node-1", SwitchState.STRAIGHT));
        switches.put("sw-2", new Switch("sw-2", "node-1", SwitchState.STRAIGHT));
    }

    public void upsertNodeStatus(String nodeId, String status) {
        nodes.put(nodeId, new Node(nodeId, status));
    }

    public void upsertSwitchState(String switchId, String nodeId, SwitchState state) {
        switches.put(switchId, new Switch(switchId, nodeId, state));
    }

    public void addEvent(Object event) {
        if (recentEvents.size() >= 200) {
            recentEvents.removeFirst();
        }
        recentEvents.addLast(event);
    }

    public List<Node> nodes() { return new ArrayList<>(nodes.values()); }
    public List<Reader> readers() { return new ArrayList<>(readers.values()); }
    public List<Switch> switches() { return new ArrayList<>(switches.values()); }
    public List<Object> recentEvents() { return new ArrayList<>(recentEvents); }
}
