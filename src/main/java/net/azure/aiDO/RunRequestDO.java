package net.azure.aiDO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RunRequestDO  (
        @JsonProperty("assistant_id")
        String assistantId) {}
