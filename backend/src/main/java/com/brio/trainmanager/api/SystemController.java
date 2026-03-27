package com.brio.trainmanager.api;

import com.brio.trainmanager.application.ManualControlService;
import com.brio.trainmanager.application.SystemStateService;
import com.brio.trainmanager.domain.SwitchCommand;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.brio.trainmanager.web.EventsStreamService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class SystemController {
    private final SystemStateService stateService;
    private final ManualControlService manualControlService;
    private final EventsStreamService streamService;

    public SystemController(SystemStateService stateService,
                            ManualControlService manualControlService,
                            EventsStreamService streamService) {
        this.stateService = stateService;
        this.manualControlService = manualControlService;
        this.streamService = streamService;
    }

    @GetMapping("/state")
    public SystemStateDto state() {
        return stateService.currentState();
    }

    @PostMapping("/switches/manual")
    public SwitchCommand manualSwitch(@Valid @RequestBody ManualSwitchRequest request) {
        return manualControlService.setSwitch(request.nodeId(), request.switchId(), request.targetState());
    }

    @GetMapping("/events/stream")
    public SseEmitter stream() {
        return streamService.connect();
    }
}
