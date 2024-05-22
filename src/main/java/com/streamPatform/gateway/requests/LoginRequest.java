package com.streamPatform.gateway.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginRequest(
        @JsonProperty("user_name") String userName ,
        @JsonProperty("password") String password) {
}
