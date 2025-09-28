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
    @GetMapping()
    public Page<ResponseDTO> getRequests(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) RequestType type,
            @RequestParam(required = false) String search,

            Pageable pageable) {

        // just pass params as-is, no specs
        return requestService.getRequests(status, type,search, pageable);
    }


    @PutMapping("/response")
    @PreAuthorize("hasAuthority('IT') || hasAuthority('ADMIN') ")
    public ResponseEntity<ResponseDTO> respondToMaintenanceRequest(
            @Valid  @RequestBody  ResponseDTO response) {
        return ResponseEntity.ok(
                requestService.respondToRequest(response)
        );
    }
}