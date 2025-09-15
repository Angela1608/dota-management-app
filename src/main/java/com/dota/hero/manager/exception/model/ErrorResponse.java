package com.dota.hero.manager.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Fault fault;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fault {

        private String faultId;
        private String traceId;

        private List<ErrorDetails> errors;

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ErrorDetails {

            private String errorCode;
            private String fieldName;
            private String description;
        }
    }

}
