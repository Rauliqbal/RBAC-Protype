package com.vensys.demo.DTO.responses;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
  private String name;
  private String description;
  private List<PermissionResponse> permissions;
}
