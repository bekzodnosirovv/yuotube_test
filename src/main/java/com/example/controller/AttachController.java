package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/open/upload")
    public ResponseEntity<ApiResponseDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping(value = "/open/{id}/img", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> loadById(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.loadImageById(id));
    }

    @GetMapping(value = "/open/{id}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> openByIdGeneral(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.loadByIdGeneral(id));
    }

    @GetMapping("/open/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/get")
    public ResponseEntity<PageImpl<AttachDTO>> pagination(@RequestParam("page") int page,
                                                          @RequestParam("size") int size) {
        return ResponseEntity.ok(attachService.pagination(page - 1, size));
    }
    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }
}
