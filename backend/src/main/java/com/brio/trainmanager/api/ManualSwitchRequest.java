package com.brio.trainmanager.api;

import com.brio.trainmanager.domain.SwitchState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManualSwitchRequest(
        @NotBlank String nodeId,
        @NotBlank String switchId,
        @NotNull SwitchState targetState
) {}
