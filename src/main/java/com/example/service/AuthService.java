package com.example.service;

import com.example.dto.*;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.repository.EmailHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponseDTO registration(RegDTO regDTO) {
        int countSenderEmail = emailHistoryRepository.countSenderEmail(regDTO.getEmail(), LocalDateTime.now().minusMinutes(1));
        if (countSenderEmail >= 4) return new ApiResponseDTO(true, "Please 1 minute try again");
        Optional<ProfileEntity> optional = profileRepository.findAllByEmailAndVisibleTrue(regDTO.getEmail());
        if (optional.isPresent()) {
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                return new ApiResponseDTO(true, "This email is already taken!");
            } else profileRepository.delete(entity.getId());
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(regDTO.getName());
        entity.setSurname(regDTO.getSurname());
        entity.setEmail(regDTO.getEmail());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setPassword(md5Util.encode(regDTO.getPassword()));
        if (regDTO.getPhoto() != null) entity.setImage_id(regDTO.getPhoto());
        profileRepository.save(entity);
        emailSenderService.senderEmail(entity);
        return new ApiResponseDTO("The verification link was send to your email");
    }

    public String verification(String jwt) {
        JwtDTO jwtDTO = jwtUtil.decode(jwt);
        Optional<ProfileEntity> optional = profileRepository.findAllByEmailAndVisibleTrue(jwtDTO.getEmail());
        if (optional.isEmpty()) return "Error : < Please try to register again >";
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) return "Error : < Verification link used >";
        profileRepository.updateStatus(ProfileStatus.ACTIVE, entity.getId());
        return "You have been successfully verified!";
    }

    public ApiResponseDTO authorization(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findAllByEmailAndVisibleTrue(authDTO.getEmail());
        if (optional.isEmpty()) return new ApiResponseDTO(true, "Profile not found");
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            return new ApiResponseDTO(true, "Profile not active");
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setEmail(entity.getEmail());
        profileDTO.setJwt(jwtUtil.encode(entity.getId(), entity.getEmail()));
        return new ApiResponseDTO(profileDTO);
    }

}
