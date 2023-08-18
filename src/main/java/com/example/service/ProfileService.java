package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileUpdateDetailDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.CustomUserDetailsUtil;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private CustomUserDetailsUtil customUserDetailsUtil;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponseDTO changePassword(String password) {
        ProfileEntity profile = customUserDetailsUtil.getCurrentUser().getProfile();
        Optional<ProfileEntity> optional = profileRepository.findAllByIdAndVisibleTrue(profile.getId());
        if (optional.isEmpty()) return new ApiResponseDTO(true, "Profile not found");
        if (optional.get().getPassword().equals(md5Util.encode(password)))
            return new ApiResponseDTO(true, "Enter a new password");
        profileRepository.changePassword(profile.getId(), md5Util.encode(password));
        return new ApiResponseDTO(profile);
    }

    public ApiResponseDTO updateEmail(String email) {
        ProfileEntity profile = customUserDetailsUtil.getCurrentUser().getProfile();
        if (profile.getEmail().equals(email)) return new ApiResponseDTO(true, "You are using this email");
        emailSenderService.updateEmailSender(profile, email);
        return new ApiResponseDTO("A verification code has been sent to your email");
    }

    public String updateEmailVerification(String jwt) {
        JwtDTO jwtDTO = jwtUtil.decode(jwt);
        Optional<ProfileEntity> optional = profileRepository.findAllByIdAndVisibleTrue(jwtDTO.getId());
        if (optional.isEmpty()) throw new ItemNotFoundException("Profile not found");
        ProfileEntity entity = optional.get();
        if (entity.getEmail().equals(jwtDTO.getEmail())) return "Verification link expired";
        profileRepository.updateEmail(entity.getId(), jwtDTO.getEmail());
        return "Your email has been successfully changed";
    }

    public ApiResponseDTO updateDetail(ProfileUpdateDetailDTO detailDTO) {
        ProfileEntity profile = customUserDetailsUtil.getCurrentUser().getProfile();
        Optional<ProfileEntity> optional = profileRepository.findAllByIdAndVisibleTrue(profile.getId());
        if (optional.isEmpty()) return new ApiResponseDTO(true, "Profile not found");
        profileRepository.updateDetail(profile.getId(), detailDTO.getName(), detailDTO.getSurname());
        return new ApiResponseDTO("");
    }

    public ProfileDTO getProfile(ProfileEntity entity) {
        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setEmail(entity.getEmail());
        return profileDTO;
    }
}
