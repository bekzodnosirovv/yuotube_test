package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.TagDTO;
import com.example.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.create(tagDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") Integer tagId,
                                                 @Valid @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.update(tagId, tagDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(tagService.delete(tagId));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponseDTO> get() {
        return ResponseEntity.ok(tagService.getAll());
    }
}
