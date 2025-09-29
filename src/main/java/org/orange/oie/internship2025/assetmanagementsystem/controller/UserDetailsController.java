package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDetailsDTO;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.UsersDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userdetails")
public class UserDetailsController {

  private final UsersDetailsService usersDetailsService;

  public UserDetailsController(UsersDetailsService usersDetailsService) {
    this.usersDetailsService = usersDetailsService;
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable Long id) {
    UserDetailsDTO dto = usersDetailsService.getUserDetailsById(id);
    return ResponseEntity.ok(dto);
  }
}

