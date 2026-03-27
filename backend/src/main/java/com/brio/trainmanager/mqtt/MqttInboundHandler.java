package com.brio.trainmanager.mqtt;

import com.brio.trainmanager.domain.RfidEvent;
import com.brio.trainmanager.domain.SwitchState;
import com.brio.trainmanager.rule.SimpleRuleEngine;
import com.brio.trainmanager.store.InMemoryStateStore;
import com.brio.trainmanager.web.EventsStreamService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MqttInboundHandler {
    private final ObjectMapper objectMapper;
    private final InMemoryStateStore store;
    private final SimpleRuleEngine ruleEngine;
    private final CommandPublisher commandPublisher;
    private final EventsStreamService streamService;

    public MqttInboundHandler(ObjectMapper objectMapper,
                              InMemoryStateStore store,
                              SimpleRuleEngine ruleEngine,
                              CommandPublisher commandPublisher,
                              EventsStreamService streamService) {
        this.objectMapper = objectMapper;
        this.store = store;
        this.ruleEngine = ruleEngine;
        this.commandPublisher = commandPublisher;
        this.streamService = streamService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handle(Message<String> message) {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload();

        try {
            JsonNode root = objectMapper.readTree(payload);
            if (topic != null && topic.contains("/heartbeat")) {
                String nodeId = root.path("nodeId").asText("unknown");
                String status = root.path("status").asText("ONLINE");
                store.upsertNodeStatus(nodeId, status);
                store.addEvent(root);
                streamService.publish("heartbeat", root);
                return;
            }

            if (topic != null && topic.contains("rfid.detected")) {
                RfidEvent event = new RfidEvent(
                        root.path("eventId").asText("unknown"),
                        root.path("nodeId").asText("unknown"),
                        root.path("readerId").asText("unknown"),
                        root.path("tagUid").asText(""),
                        Instant.parse(root.path("ts").asText(Instant.now().toString()))
                );
                store.addEvent(event);
                streamService.publish("rfid.detected", event);
                ruleEngine.evaluate(event).ifPresent(cmd -> {
                    commandPublisher.publishSwitchCommand(event.nodeId(), cmd);
                    store.addEvent(cmd);
                    streamService.publish("switch.command", cmd);
                });
                return;
            }

            if (topic != null && topic.contains("/state/switch")) {
                String switchId = root.path("switchId").asText("unknown");
                String nodeId = root.path("nodeId").asText("unknown");
                SwitchState state = SwitchState.valueOf(root.path("state").asText("STRAIGHT"));
                store.upsertSwitchState(switchId, nodeId, state);
                store.addEvent(root);
                streamService.publish("switch.state", root);
            }
        } catch (Exception ex) {
            store.addEvent("MQTT_PARSE_ERROR: " + ex.getMessage());
        }
    }
}
