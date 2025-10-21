package com.raxrot.teacher_service.exception;

import java.util.Map;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
}
