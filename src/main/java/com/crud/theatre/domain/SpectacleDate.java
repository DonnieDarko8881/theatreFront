package com.crud.theatre.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SpectacleDate {
    private long id;
    private String date;
    private long spectacleId;
    private String spectacleName;
    private long stageId;
    private String stageName;
    private StageCopyDto stageCopy;

}
