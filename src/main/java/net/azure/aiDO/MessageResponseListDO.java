package net.azure.aiDO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MessageResponseListDO(
        String object,
        List<MessageResponseDO> data,
        @JsonProperty("first_id")
        String firstId,
        @JsonProperty("last_id")
        String lastId,
        @JsonProperty("has_more")

        boolean hasMore) {}