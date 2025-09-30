package org.orange.oie.internship2025.assetmanagementsystem.controller;


import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/requests")
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
    @GetMapping("/pending")
    public Page<ResponseDTO> getPendingRequests(
            @RequestParam(required = false) RequestType type,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return requestService.getRequests(List.of(RequestStatus.PENDING), type,search, pageable,false);
    }
    @GetMapping("/history")
    public Page<ResponseDTO> getRequestsHistory(
            @RequestParam(required = false) RequestType type,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return requestService.getRequests( List.of(RequestStatus.APPROVED, RequestStatus.REJECTED), type,search, pageable,false);
    }
    @GetMapping("/my-requests")
    public Page<ResponseDTO> myRequests(
            @RequestParam(required = false) RequestType type,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return requestService.getRequests( null, type,search, pageable,true
        );
    }


    @PutMapping("/response")
    @PreAuthorize("hasAuthority('IT') || hasAuthority('ADMIN') ")
    public ResponseEntity<ResponseDTO> respondToRequests(
            @Valid  @RequestBody  ResponseDTO response) {
        return ResponseEntity.ok(
                requestService.respondToRequest(response)
        );
    }
}