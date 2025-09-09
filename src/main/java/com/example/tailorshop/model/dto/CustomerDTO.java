package com.example.tailorshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String phone;
    private String gender;
    private String email;
    private String address;
    private List<MeasurementDTO> measurements;
}
