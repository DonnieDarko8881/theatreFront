package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StageCopy {
    private long id;
    private String date;
    private String spectacleName;
    private List<SeatsDto> seats;
    private BigDecimal spectaclePricePLN;
}
