package com.dota.hero.manager.exception.util;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class ResponseCreator {

    private static final String TRACE_ID_HEADER = "trace-id";

    public static String getTraceIdHeader(HttpServletRequest request, Tracer tracer) {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (StringUtils.hasText(traceId)) {
            return traceId;
        }
        if (tracer != null && tracer.currentSpan() != null) {
            return tracer.currentSpan().context().traceId();
        }
        return "unknown";
    }

}
