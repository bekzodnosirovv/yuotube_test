package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import com.xuggle.xuggler.IContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {

    @Value("${attach.folder.name}")
    private String folderName;
    @Value("${attach.url}")
    private String attachUrl;

    @Autowired
    private AttachRepository attachRepository;

    public ApiResponseDTO upload(MultipartFile file) {
        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);
            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setType(extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOriginName(file.getOriginalFilename());
            if (extension.equals("mp4")) {
                IContainer container = IContainer.make();
                container.open(path.toString(), IContainer.Type.READ, null);
                long duration = container.getDuration();
                entity.setDuration(duration / 1000);
                System.out.println(duration);
                container.close();
            }
            attachRepository.save(entity);
            return new ApiResponseDTO(toDTO(entity));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public byte[] loadImageById(String id) {
        AttachEntity entity = get(id);
        try {
            BufferedImage originalImage = ImageIO.read(new File(url(entity.getPath(), entity.getId(), entity.getType())));
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getType(), boas);
            boas.flush();
            byte[] imageInByte = boas.toByteArray();
            boas.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            File file = new File(url(entity.getPath(), entity.getId(), entity.getType()));
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public ResponseEntity<Resource> download(String id) {
        AttachEntity entity = get(id);
        try {
            Path file = Paths.get(url(entity.getPath(), entity.getId(), entity.getType()));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginName() + "\"").body(resource);
            } else throw new RuntimeException("Could not read the file!");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public PageImpl<AttachDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> entityPages = attachRepository.findAll(pageable);
        return new PageImpl<>(entityPages.getContent().stream().map(this::toDTO).toList(),
                pageable, entityPages.getTotalElements());
    }

    public Boolean delete(String id) {
        AttachEntity entity = get(id);
        attachRepository.delete(entity);
        File file = new File("attaches/" + entity.getPath()
                + "/" + entity.getId() + "." + entity.getType());
        if (file.exists()) return file.delete();
        return false;
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOriginName());
        attachDTO.setSize(entity.getSize());
        if (entity.getType().equals("mp4")) attachDTO.setDuration(entity.getDuration());
        attachDTO.setUrl(getUrl(entity.getId(), entity.getType()));
        return attachDTO;
    }

    public String url(String path, String id, String extension) {
        return folderName + "/" + path + "/" + id + "." + extension;
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("File not found"));
    }

    public String getUrl(String id, String extension) {
        if (extension.equals("jpg")) return attachUrl + "/open/" + id + "/img";
        return attachUrl + "/open/" + id;
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }
}
