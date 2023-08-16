package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.RegDTO;
import com.example.entity.ProfileEntity;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    public ApiResponseDTO registration(RegDTO regDTO) {

        return null;
    }

    public ApiResponseDTO authorization(AuthDTO authDTO) {
        return null;
    }

    public String verification(String jwt) {
        return null;
    }
}
