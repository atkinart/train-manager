package com.brio.trainmanager.web;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventsStreamService {
    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    public void publish(String eventName, Object payload) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(payload));
            } catch (IOException e) {
                emitter.complete();
            }
        });
    }
}
