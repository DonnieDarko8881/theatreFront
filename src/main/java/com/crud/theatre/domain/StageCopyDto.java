package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StageCopyDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("seats")
    private List<SeatsDto> seats;
    @JsonProperty("spectacleDateDto")
    private SpectacleDateDto spectacleDateDto;
    @JsonProperty("spectaclePricePLN")
    private BigDecimal spectaclePricePLN;

    @Override
    public String toString() {
        return "Copy Id = " + id;
    }
}
