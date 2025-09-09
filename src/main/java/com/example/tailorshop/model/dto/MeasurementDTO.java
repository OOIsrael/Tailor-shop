package com.example.tailorshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
    private Long id;
    private String type;
    private String value;
}
