package com.brio.trainmanager.mqtt;

import com.brio.trainmanager.domain.SwitchCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class CommandPublisher {
    private final MessageChannel mqttOutboundChannel;
    private final MqttProperties properties;
    private final ObjectMapper objectMapper;

    public CommandPublisher(MessageChannel mqttOutboundChannel, MqttProperties properties, ObjectMapper objectMapper) {
        this.mqttOutboundChannel = mqttOutboundChannel;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    public void publishSwitchCommand(String nodeId, SwitchCommand command) {
        String topic = properties.getTopics().getSwitchCommandTemplate().formatted(nodeId);
        try {
            String payload = objectMapper.writeValueAsString(command);
            mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .setHeader("mqtt_qos", 1)
                    .build());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize switch command", e);
        }
    }
}
