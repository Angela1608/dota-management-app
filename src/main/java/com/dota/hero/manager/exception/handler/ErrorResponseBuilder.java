package com.dota.hero.manager.exception.handler;

import com.dota.hero.manager.exception.model.ErrorResponse;
import com.dota.hero.manager.exception.util.ResponseCreator;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ErrorResponseBuilder {

    private final Tracer tracer;

    public ErrorResponse buildErrorResponse(List<ErrorResponse.Fault.ErrorDetails> details,
                                            HttpServletRequest request) {
        String traceId = ResponseCreator.getTraceIdHeader(request, tracer);
        return ErrorResponse.builder()
                .fault(ErrorResponse.Fault.builder()
                        .faultId(UUID.randomUUID().toString())
                        .traceId(traceId)
                        .errors(details)
                        .build())
                .build();
    }

}
