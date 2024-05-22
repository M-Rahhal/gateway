package com.streamPatform.gateway.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequest(
        @JsonProperty("user_name") String userName ,
        @JsonProperty("password") String password ,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName
) {
}
