package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ProfileUpdateDetailDTO;
import com.example.repository.ProfileRepository;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PutMapping("/change")
    public ResponseEntity<ApiResponseDTO> changePassword(@RequestParam("password") String password) {
        return ResponseEntity.ok(profileService.changePassword(password));
    }

    @PutMapping("/update-email")
    public ResponseEntity<ApiResponseDTO> updateEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(profileService.updateEmail(email));
    }

    @PutMapping("/update-email/verification/{jwt}")
    public ResponseEntity<String> updateEmailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(profileService.updateEmailVerification(jwt));
    }

    @PutMapping("/update-detail")
    public ResponseEntity<ApiResponseDTO> updateDetail(@RequestBody ProfileUpdateDetailDTO detailDTO) {
      return ResponseEntity.ok(profileService.updateDetail(detailDTO));
    }
}
