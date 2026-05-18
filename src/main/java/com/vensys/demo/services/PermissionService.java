package com.vensys.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vensys.demo.DTO.requests.PermissionRequest;
import com.vensys.demo.entities.Permission;
import com.vensys.demo.repositories.PermissionRepository;

import jakarta.transaction.Transactional;

@Service
public class PermissionService {
  @Autowired
  private PermissionRepository permissionRepository;

  // CREATE
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

  // GET ALL
  @Transactional
  public List<Permission> getAll() {
    return permissionRepository.findAll();
  }

  // GET by ID
  @Transactional
  public Permission getById(@NonNull Long id) {
    return permissionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Permission with id '" + id + "' not found."));
  }

  // UPDATE
  @Transactional
  public Permission update(@NonNull Long id, PermissionRequest request) {
    Permission permission = getById(id);

    if (!permission.getName().equals(request.getName()) && permissionRepository.existsByName(request.getName())) {
      throw new IllegalArgumentException("Permission with name '" + request.getName() + "' already exists.");
    }

    permission.setName(request.getName());
    permission.setDescription(request.getDescription());
    return permissionRepository.save(permission);
  }

  // DELETE
  @Transactional
  public void delete(@NonNull Long id) {
    if (!permissionRepository.existsById(id)) {
      throw new IllegalArgumentException("Permission with id '" + id + "' not found."); 
    }

    permissionRepository.deleteById(id);
  }
}
