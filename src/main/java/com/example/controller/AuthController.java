package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.RegDTO;
import com.example.service.AuthService;
import com.example.service.EmailSenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<ApiResponseDTO> registration(@Valid @RequestBody RegDTO regDTO) {
        return ResponseEntity.ok(authService.registration(regDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> authorization(@Valid @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(authService.authorization(authDTO));
    }

    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<String> verification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.verification(jwt));
    }

}
