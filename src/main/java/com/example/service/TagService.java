package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public ApiResponseDTO create(TagDTO tagDTO) {
        TagEntity entity = new TagEntity();
        entity.setName(entity.getName());
        tagRepository.save(entity);
        tagDTO.setId(entity.getId());
        return new ApiResponseDTO(tagDTO);
    }

    public ApiResponseDTO update(Integer tagId, TagDTO tagDTO) {
        if (tagId == null) return new ApiResponseDTO(true, "Tag id is null");
        if (get(tagId) == null) return new ApiResponseDTO(true, "Tag not found");
        if (tagRepository.update(tagId, tagDTO.getName()) == 0) return new ApiResponseDTO(true, "Tag nt update");
        tagDTO.setId(tagId);
        return new ApiResponseDTO(tagDTO);
    }

    public ApiResponseDTO delete(Integer tagId) {
        if (tagId == null) return new ApiResponseDTO(true, "Tag id is null");
        if (get(tagId) == null) return new ApiResponseDTO(true, "Tag not found");
        tagRepository.delete(tagId);
        return new ApiResponseDTO(tagId);
    }

    public ApiResponseDTO getAll() {
        List<TagEntity> entityList = tagRepository.findAllByVisibleTrue();
        return new ApiResponseDTO(entityList.stream().map(this::toDTO).toList());
    }

    private TagDTO toDTO(TagEntity entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }

    public TagDTO get(Integer tagId) {
        if (tagId == null) return null;
        Optional<TagEntity> optional = tagRepository.findAllByIdAndVisibleTrue(tagId);
        return optional.map(entity -> new TagDTO(tagId, entity.getName())).orElse(null);
    }


}
