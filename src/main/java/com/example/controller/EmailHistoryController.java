package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.EmailFilterDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email/history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponseDTO> get(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(emailHistoryService.get(page - 1, size));
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<ApiResponseDTO> getByEmail(@PathVariable("email") String email,
                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email,page - 1, size));
    }
    @GetMapping("/filter")
    public ResponseEntity<ApiResponseDTO> filter(@RequestBody EmailFilterDTO emailFilterDTO,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(emailHistoryService.filter(emailFilterDTO,page-1,size));
    }
}
