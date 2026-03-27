package com.brio.trainmanager.application;

import com.brio.trainmanager.api.SystemStateDto;
import com.brio.trainmanager.store.InMemoryStateStore;
import org.springframework.stereotype.Service;

@Service
public class SystemStateService {
    private final InMemoryStateStore store;

    public SystemStateService(InMemoryStateStore store) {
        this.store = store;
    }

    public SystemStateDto currentState() {
        return new SystemStateDto(store.nodes(), store.readers(), store.switches(), store.recentEvents());
    }
}
