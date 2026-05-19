package com.vensys.demo.DTO.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
  private String name;
  private String description;
  private Set<PermissionResponse> permissions;
}
