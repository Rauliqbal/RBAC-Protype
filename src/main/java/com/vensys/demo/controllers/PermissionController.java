package com.vensys.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private PermissionRepository permissionRepository;

  @PostMapping("/permissions")
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
}
