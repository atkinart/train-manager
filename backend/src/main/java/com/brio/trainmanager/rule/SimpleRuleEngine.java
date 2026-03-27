package com.brio.trainmanager.rule;

import com.brio.trainmanager.domain.RfidEvent;
import com.brio.trainmanager.domain.SwitchCommand;
import com.brio.trainmanager.domain.SwitchState;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleRuleEngine {
    public Optional<SwitchCommand> evaluate(RfidEvent event) {
        if ("reader-a".equals(event.readerId()) && "04AABBCCDD".equalsIgnoreCase(event.tagUid())) {
            return Optional.of(new SwitchCommand(
                    UUID.randomUUID().toString(),
                    "sw-1",
                    SwitchState.DIVERGE,
                    "rule:reader-a-tag-04AABBCCDD",
                    Instant.now()
            ));
        }
        return Optional.empty();
    }
}
