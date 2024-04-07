package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.enums.ResultModelAttribute;
import com.udacity.jwdnd.course1.cloudstorage.enums.ServiceResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/home/add-file")
    public String addFile(Authentication authentication,
                          @RequestParam(value = "fileUpload") MultipartFile fileUpload,
                          Model model) {
        try {
            Integer userId = this.userService.getUser(authentication.getName()).getId();
            if (this.fileService.getFile(userId, fileUpload.getOriginalFilename()) != null) {
                model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), ServiceResponse.FILE_DUPLICATE_ERROR.getStatusString());
                return "result";
            }
            byte[] fileBytes = fileUpload.getInputStream().readAllBytes();
            File file = new File();
            file.setName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setSize(fileUpload.getSize());
            file.setData(fileBytes);
            file.setUserId(userId);
            this.fileService.addFile(file);
            model.addAttribute(ResultModelAttribute.SUCCESS.getStatusString(), true);
        } catch (Exception e) {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), ServiceResponse.FILE_UPLOAD_ERROR.getStatusString());
        }
        return "result";
    }

    @GetMapping("/home/download-file")
    public ResponseEntity<ByteArrayResource> downloadFile(Authentication authentication,
                                                          @RequestParam(value = "id") String id,
                                                          Model model) {
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        Integer fileId = Integer.parseInt(id);
        File file = this.fileService.getFile(fileId);
        if (!file.getUserId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping("/home/delete-file")
    public String deleteFile(Authentication authentication,
                             @RequestParam(value = "id") String id,
                             Model model) {
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        Integer fileId = Integer.parseInt(id);
        File file = this.fileService.getFile(fileId);
        if (!file.getUserId().equals(userId)) {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), "User is not authorized to delete file.");
            return "result";
        }
        ServiceResponse response = this.fileService.deleteFile(fileId);
        if (response.equals(ServiceResponse.SUCCESS)) {
            model.addAttribute(ResultModelAttribute.SUCCESS.getStatusString(), true);
        } else {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), response.getStatusString());
        }
        return "result";
    }
}
