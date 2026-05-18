package com.vensys.demo.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PermissionRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}
