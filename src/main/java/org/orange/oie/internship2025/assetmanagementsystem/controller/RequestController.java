package org.orange.oie.internship2025.assetmanagementsystem.controller;


import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
