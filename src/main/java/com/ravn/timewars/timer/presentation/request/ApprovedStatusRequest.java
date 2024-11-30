package com.ravn.timewars.timer.presentation.request;

import jakarta.validation.constraints.NotNull;

public record ApprovedStatusRequest(
        @NotNull Integer approvedStatus,
        @NotNull Long userId
) {
}
