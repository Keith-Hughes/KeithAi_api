package net.azure.aiDO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record AiThreadDO(
        String id,
        String object,
        @JsonProperty("created_at")
        long createdAt,
        Map<String, Object> metadata) {
}
