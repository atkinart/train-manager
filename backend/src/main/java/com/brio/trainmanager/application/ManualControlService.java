package com.brio.trainmanager.application;

import com.brio.trainmanager.domain.SwitchCommand;
import com.brio.trainmanager.domain.SwitchState;
import com.brio.trainmanager.mqtt.CommandPublisher;
import com.brio.trainmanager.store.InMemoryStateStore;
import com.brio.trainmanager.web.EventsStreamService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ManualControlService {
    private final CommandPublisher commandPublisher;
    private final InMemoryStateStore store;
    private final EventsStreamService streamService;

    public ManualControlService(CommandPublisher commandPublisher, InMemoryStateStore store, EventsStreamService streamService) {
        this.commandPublisher = commandPublisher;
        this.store = store;
        this.streamService = streamService;
    }

    public SwitchCommand setSwitch(String nodeId, String switchId, SwitchState targetState) {
        SwitchCommand command = new SwitchCommand(
                UUID.randomUUID().toString(),
                nodeId,
                switchId,
                targetState,
                "manual-api",
                Instant.now());
        commandPublisher.publishSwitchCommand(nodeId, command);
        store.addEvent(command);
        streamService.publish("switch.command", command);
        return command;
    }
}
