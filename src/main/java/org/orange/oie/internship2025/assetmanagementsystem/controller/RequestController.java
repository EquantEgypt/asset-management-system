package org.orange.oie.internship2025.assetmanagementsystem.controller;


import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.*;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;
    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> addRequest(@Valid @RequestBody RequestDTO requestDTO) {
        ResponseDTO response =  requestService.addRequest(requestDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public Page<ResponseDTO> getRequests(RequestFilter filter, Pageable pageable) {
        return requestService.getRequests(filter, pageable);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('IT') || hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> approveRequest(
            @PathVariable Long id,
            @Valid @RequestBody ApproveRequestDTO dto) {
        return ResponseEntity.ok(requestService.approveRequest(id, dto));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('IT') || hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> rejectRequest(
            @PathVariable Long id,
            @Valid @RequestBody RejectRequestDTO dto) {
        return ResponseEntity.ok(requestService.rejectRequest(id, dto));
    }
}