package com.vensys.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vensys.demo.DTO.requests.PermissionRequest;
import com.vensys.demo.entities.Permission;
import com.vensys.demo.repositories.PermissionRepository;

import jakarta.transaction.Transactional;

@Service
public class PermissionService {
  @Autowired
  private PermissionRepository permissionRepository;

  @Transactional
  public Permission create(PermissionRequest request) {
    if (permissionRepository.existsByName(request.getName())) {
      throw new IllegalArgumentException("Permission with name '" + request.getName() + "' already exists.");
    }

    Permission permission = new Permission();
    permission.setName(request.getName());
    permission.setDescription(request.getDescription());
    return permissionRepository.save(permission);
  }
}
