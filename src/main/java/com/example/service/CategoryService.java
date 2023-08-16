package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponseDTO create(CategoryDTO categoryDTO) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoryDTO.getName());
        categoryRepository.save(entity);
        categoryDTO.setId(entity.getId());
        return new ApiResponseDTO(categoryDTO);
    }

    public ApiResponseDTO update(Integer categoryId, CategoryDTO categoryDTO) {
        if (categoryId == null) return new ApiResponseDTO(true, "Category id is null");
        if (get(categoryId) == null) return new ApiResponseDTO(true, "Category not found");
        if (categoryRepository.update(categoryId, categoryDTO.getName()) == 0) {
            return new ApiResponseDTO(true, "Category not update");
        }
        categoryDTO.setId(categoryId);
        return new ApiResponseDTO(categoryDTO);
    }

    public ApiResponseDTO delete(Integer categoryId) {
        if (categoryId == null) return new ApiResponseDTO(true, "Category id is null");
        if (get(categoryId) == null) return new ApiResponseDTO(true, "Category not found");
        categoryRepository.delete(categoryId);
        return new ApiResponseDTO(categoryId);
    }

    public ApiResponseDTO getList() {
        List<CategoryEntity> entityList = categoryRepository.findAllByVisibleTrue();
        return new ApiResponseDTO(entityList.stream().map(this::toDTO).toList());
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        return new CategoryDTO(entity.getId(), entity.getName());
    }

    public CategoryDTO get(Integer categoryId) {
        if (categoryId == null) return null;
        Optional<CategoryEntity> optional = categoryRepository.findAllByIdAndVisibleTrue(categoryId);
        return optional.map(entity -> new CategoryDTO(categoryId, entity.getName())).orElse(null);
    }

}
