package com.vensys.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vensys.demo.DTO.requests.PermissionRequest;
import com.vensys.demo.DTO.responses.PermissionResponse;
import com.vensys.demo.DTO.responses.RestResponse;
import com.vensys.demo.entities.Permission;
import com.vensys.demo.repositories.PermissionRepository;
import com.vensys.demo.services.PermissionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private PermissionRepository permissionRepository;

  // CREATE
  @PostMapping("/permission")
  public RestResponse<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
    Permission permission = permissionService.create(request);

    PermissionResponse response = PermissionResponse.builder()
        .name(permission.getName())
        .description(permission.getDescription())
        .build();

    return RestResponse.<PermissionResponse>builder()
        .success(true)
        .message("Create success!")
        .data(response)
        .build();
  }

  // GET ALL
  @GetMapping("/permission")
  public RestResponse<List<PermissionResponse>> getAll() {
    List<Permission> permissions = permissionService.getAll();
    List<PermissionResponse> responseList = permissions.stream()
        .map(permission -> PermissionResponse.builder()
            .name(permission.getName())
            .description(permission.getDescription())
            .build())
        .collect(Collectors.toList());

    return RestResponse.<List<PermissionResponse>>builder()
        .success(true)
        .message("Get all permissions success!")
        .data(responseList)
        .build();
  }

  // GET by ID
  @GetMapping("/permission/{id}")
  public RestResponse<PermissionResponse> getById(@PathVariable("id") @NonNull Long id) {
    Permission permission = permissionService.getById(id);
    PermissionResponse response = PermissionResponse.builder()
        .name(permission.getName())
        .description(permission.getDescription())
        .build();

    return RestResponse.<PermissionResponse>builder()
        .success(true)
        .message("Get permission by id success!")
        .data(response)
        .build();
  }

  // UPDATE
  @PostMapping("/permission/{id}")
  public RestResponse<PermissionResponse> update(@PathVariable("id") @NonNull Long id,
      @Valid @RequestBody PermissionRequest request) {
    Permission permission = permissionService.update(id, request);
    PermissionResponse response = PermissionResponse.builder()
        .name(permission.getName())
        .description(permission.getDescription())
        .build();

    return RestResponse.<PermissionResponse>builder()
        .success(true)
        .message("Update permission success!")
        .data(response)
        .build();
  }

  // DELETE
  @DeleteMapping("/permission/{id}")
  public RestResponse<Void> delete(@PathVariable("id") @NonNull Long id) {
    permissionService.delete(id);
    return RestResponse.<Void>builder()
        .success(true)
        .message("Delete permission success!")
        .build();
  }
}