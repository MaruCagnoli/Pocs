package com.poc.PocJPAyRedis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table()
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RealState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String code;

    private BigDecimal price;

    private String operationType;

    private Integer roomsQuantity;

    private Integer bathroomsQuantity;

    private LocalDateTime lastUpdate;

    private BigDecimal area;
}
