package com.example.signals.dtos;

import com.example.signals.models.DeadbandType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignalDto {
    private String nodeId;
    private Integer samplingIntervalMs;
    private Integer deadbandValue;
    private DeadbandType deadbandType;
    private Boolean active;
    private int[] keywordsIds;
}
