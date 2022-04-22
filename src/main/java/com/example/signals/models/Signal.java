package com.example.signals.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "signals")
@Entity
@Getter
@Setter
public class Signal {
    @Id
    @Column(name = "node_id", nullable = false)
    private String nodeId;

    @Column(name = "sampling_interval_ms", nullable = false)
    private Integer samplingIntervalMs;

    @Column(name = "deadband_value")
    private Integer deadbandValue;

    @Column(name = "deadband_type")
    private DeadbandType deadbandType;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "keyword", targetEntity = Keyword.class)
    private List<Keyword> keywords;
}
