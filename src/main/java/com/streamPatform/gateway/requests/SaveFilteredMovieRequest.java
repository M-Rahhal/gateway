package com.streamPatform.gateway.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SaveFilteredMovieRequest(
        @JsonProperty("movie-name") String movieName,
        @JsonProperty("movie-path") String moviePath) {
}
