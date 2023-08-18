package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.EmailFilterDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void sendEmailSave(String message, String email, String subject) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setMessage(subject);
        entity.setToEmail(email);
        entity.setTitle(message);
        emailHistoryRepository.save(entity);
    }

    public ApiResponseDTO get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<EmailHistoryEntity> entityPages = emailHistoryRepository.findAll(pageable);
        return new ApiResponseDTO(new PageImpl<>(entityPages.getContent().stream()
                .map(this::toDTO).toList(), pageable, entityPages.getTotalElements()));
    }

    public ApiResponseDTO getByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<EmailHistoryEntity> entityPages = emailHistoryRepository.findAllByToEmail(email, pageable);
        return new ApiResponseDTO(new PageImpl<>(entityPages.getContent().stream()
                .map(this::toDTO).toList(), pageable, entityPages.getTotalElements()));
    }

    public ApiResponseDTO filter(EmailFilterDTO emailFilterDTO, int page, int size) {
        if (emailFilterDTO.getEmail() == null && emailFilterDTO.getCreatedDate() == null) {
            return new ApiResponseDTO(true, "No results found");
        }
        if (emailFilterDTO.getEmail() != null && emailFilterDTO.getCreatedDate() == null) {
            return getByEmail(emailFilterDTO.getEmail(), page, size);
        }
        if (emailFilterDTO.getEmail() == null) {
            return getByCreatedDate(emailFilterDTO.getCreatedDate(), page, size);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<EmailHistoryEntity> entityPages = emailHistoryRepository.findAllByToEmailAndCreatedDateBetween(emailFilterDTO.getEmail(),
                LocalDateTime.of(emailFilterDTO.getCreatedDate(), LocalTime.MIN),
                LocalDateTime.of(emailFilterDTO.getCreatedDate(), LocalTime.MAX), pageable);
        return new ApiResponseDTO(new PageImpl<>(entityPages.getContent().stream()
                .map(this::toDTO).toList(), pageable, entityPages.getTotalElements()));
    }

    public ApiResponseDTO getByCreatedDate(LocalDate createdDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<EmailHistoryEntity> entityPages = emailHistoryRepository.findAllByCreatedDateBetween(
                LocalDateTime.of(createdDate, LocalTime.MIN), LocalDateTime.of(createdDate, LocalTime.MAX), pageable);
        return new ApiResponseDTO(new PageImpl<>(entityPages.getContent().stream()
                .map(this::toDTO).toList(), pageable, entityPages.getTotalElements()));
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO emailHistoryDTO = new EmailHistoryDTO();
        emailHistoryDTO.setId(entity.getId());
        emailHistoryDTO.setToEmail(entity.getToEmail());
        emailHistoryDTO.setTitle(entity.getTitle());
        emailHistoryDTO.setMessage(entity.getMessage());
        emailHistoryDTO.setCreatedDate(entity.getCreatedDate());
        return emailHistoryDTO;
    }


}
