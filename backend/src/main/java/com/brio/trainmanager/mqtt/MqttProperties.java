package com.brio.trainmanager.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    private String brokerUrl;
    private String clientId;
    private String username;
    private String password;
    private Topics topics = new Topics();

    public static class Topics {
        private String heartbeat;
        private String rfidDetected;
        private String switchState;
        private String switchCommandTemplate;

        public String getHeartbeat() { return heartbeat; }
        public void setHeartbeat(String heartbeat) { this.heartbeat = heartbeat; }
        public String getRfidDetected() { return rfidDetected; }
        public void setRfidDetected(String rfidDetected) { this.rfidDetected = rfidDetected; }
        public String getSwitchState() { return switchState; }
        public void setSwitchState(String switchState) { this.switchState = switchState; }
        public String getSwitchCommandTemplate() { return switchCommandTemplate; }
        public void setSwitchCommandTemplate(String switchCommandTemplate) { this.switchCommandTemplate = switchCommandTemplate; }
    }

    public String getBrokerUrl() { return brokerUrl; }
    public void setBrokerUrl(String brokerUrl) { this.brokerUrl = brokerUrl; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Topics getTopics() { return topics; }
    public void setTopics(Topics topics) { this.topics = topics; }
}
