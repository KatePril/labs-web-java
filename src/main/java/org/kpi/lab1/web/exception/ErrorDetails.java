package org.kpi.lab1.web.exception;

public record ErrorDetails(
        int status,
        String error,
        String message,
        String path
) {
}
