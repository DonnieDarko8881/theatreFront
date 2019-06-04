package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatsDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("number")
    private int number;
    @JsonProperty("stageCopyId")
    private Long stageCopyId;
    @JsonProperty("status")
    private String status;
}
