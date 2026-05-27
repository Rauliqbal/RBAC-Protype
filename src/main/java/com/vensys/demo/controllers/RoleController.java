package com.vensys.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vensys.demo.DTO.responses.PermissionResponse;
import com.vensys.demo.DTO.responses.RestResponse;
import com.vensys.demo.DTO.responses.RoleResponse;
import com.vensys.demo.DTO.requests.RoleRequest;
import com.vensys.demo.entities.Role;
import com.vensys.demo.services.PermissionService;
import com.vensys.demo.services.RoleService;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @Autowired
  private PermissionService permissionService;

  // CREATE
  @PostMapping("/role")
  public RestResponse<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
    Role role = roleService.create(request);
    RoleResponse response = RoleResponse.builder()
        .name(role.getName())
        .description(role.getDescription())
        .permissions(role.getPermissions().stream()
            .map(permission -> PermissionResponse.builder().name(permission.getName())
                .description(permission.getDescription()).build())
            .collect(Collectors.toList()))
        .build();
    return RestResponse.<RoleResponse>builder()
        .success(true)
        .message("Create success!")
        .data(response)
        .build();
  }

  // GET ALL
  @GetMapping("/role")
  public RestResponse<List<RoleResponse>> getAll() {
    List<Role> roles = roleService.getAll();
    List<RoleResponse> roleList = roles.stream()
        .map(role -> RoleResponse.builder()
            .name(role.getName())
            .description(role.getDescription())
            .permissions(role.getPermissions().stream()
                .map(permission -> PermissionResponse.builder().name(permission.getName())
                    .description(permission.getDescription()).build())
                .collect(Collectors.toList()))
            .build())
        .collect(Collectors.toList());

    return RestResponse.<List<RoleResponse>>builder()
        .success(true)
        .message("Get all roles success!")
        .data(roleList)
        .build();
  }

  // GET by ID
  @GetMapping("/role/{id}")
  public RestResponse<RoleResponse> getById(@PathVariable("id") Long id) {
    Role role = roleService.getById(id);
    RoleResponse response = RoleResponse.builder()
        .name(role.getName())
        .description(role.getDescription())
        .permissions(role.getPermissions().stream()
            .map(permission -> PermissionResponse.builder().name(permission.getName())
                .description(permission.getDescription()).build())
            .collect(Collectors.toList()))
        .build();

    return RestResponse.<RoleResponse>builder()
        .success(true)
        .message("Get role by id success!")
        .data(response)
        .build();
  }

  // UPDATE
  @PatchMapping("/role/{id}")
  public RestResponse<RoleResponse> update(@PathVariable("id") Long id, @Valid @RequestBody RoleRequest request) {
    Role role = roleService.update(id, request);
    RoleResponse response = RoleResponse.builder()
        .name(role.getName())
        .description(role.getDescription())
        .permissions(role.getPermissions().stream()
            .map(permission -> PermissionResponse.builder().name(permission.getName())
                .description(permission.getDescription()).build())
            .collect(Collectors.toList()))
        .build();

    return RestResponse.<RoleResponse>builder()
        .success(true)
        .message("Update role success!")
        .data(response)
        .build();
  }

  // DELETE
  @DeleteMapping("/role/{id}")
  public RestResponse<Void> delete(@PathVariable("id") @NonNull Long id) {
    roleService.delete(id);
    return RestResponse.<Void>builder()
        .success(true)
        .message("Delete role success!")
        .build();
  }
}
