package com.example.signals.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "signals")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Signal {
    @Id
    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "sampling_interval_ms", nullable = false)
    private Integer samplingIntervalMs;

    @Column(name = "deadband_value")
    private Integer deadbandValue;

    @Column(name = "deadband_type")
    private DeadbandType deadbandType;

    @Column(name = "active", nullable = false)
    private Boolean active;

    /*
    Should better be a List with a ManyToMany relation and a join table but to gain some time I used a dummy solution
     */
    @Column(name = "keywordIds")
    private String keywordIds;
}
