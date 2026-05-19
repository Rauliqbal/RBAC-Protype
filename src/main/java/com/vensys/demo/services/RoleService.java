package com.vensys.demo.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vensys.demo.DTO.requests.RoleRequest;
import com.vensys.demo.entities.Permission;
import com.vensys.demo.entities.Role;
import com.vensys.demo.repositories.PermissionRepository;
import com.vensys.demo.repositories.RoleRepository;

import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  // CREATE
  @Transactional
  public Role create(RoleRequest request) {

    if (roleRepository.existsByName(
        request.getName())) {

      throw new IllegalArgumentException(
          "Role with name '" +
              request.getName() +
              "' already exists.");
    }

    List<Permission> permissions = permissionRepository.findAllById(
        request.getPermissionsIds());

    if (permissions.size() != request.getPermissionsIds().size()) {

      throw new IllegalArgumentException(
          "One or more permission IDs do not exist.");
    }

    Role role = new Role();
    role.setName(request.getName());
    role.setDescription(
        request.getDescription());

    role.setPermissions(
        new HashSet<>(permissions));

    return roleRepository.save(role);
  }

  // GET ALL
  @Transactional
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  // GET BY ID
  @Transactional
  public Role getById(
      @NonNull Long id) {

    return roleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(
            "Role not found with id: "
                + id));
  }

  // UPDATE
  @Transactional
  public Role update(
      @NonNull Long id,
      RoleRequest request) {

    Role role = getById(id);

    if (!role.getName().equals(
        request.getName())
        && roleRepository.existsByName(
            request.getName())) {

      throw new IllegalArgumentException(
          "Role with name '" +
              request.getName() +
              "' already exists.");
    }

    List<Permission> permissions = permissionRepository.findAllById(
        request.getPermissionsIds());

    if (permissions.size() != request.getPermissionsIds().size()) {

      throw new IllegalArgumentException(
          "One or more permission IDs do not exist.");
    }

    role.setName(
        request.getName());

    role.setDescription(
        request.getDescription());

    role.setPermissions(
        new HashSet<>(permissions));

    return roleRepository.save(role);
  }

  // DELETE
  @Transactional
  public void delete(
      @NonNull Long id) {

    Role role = getById(id);
    roleRepository.delete(role);
  }
}