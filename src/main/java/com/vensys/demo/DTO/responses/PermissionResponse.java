package com.vensys.demo.DTO.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionResponse {
  private String name;
  private String description;
}
