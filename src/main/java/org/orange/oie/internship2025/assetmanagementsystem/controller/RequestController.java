package org.orange.oie.internship2025.assetmanagementsystem.controller;


import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.RequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {


    @PostMapping
    public ResponseEntity<ResponseDto> addRequest(@Valid @RequestBody RequestDto requestDto) {

        return ResponseEntity.ok(null);

    }

}
