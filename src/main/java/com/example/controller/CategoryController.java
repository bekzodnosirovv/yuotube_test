package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") Integer categoryId,
                                                 @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(categoryId, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(categoryService.delete(categoryId));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponseDTO> get() {
        return ResponseEntity.ok(categoryService.getList());
    }


}
