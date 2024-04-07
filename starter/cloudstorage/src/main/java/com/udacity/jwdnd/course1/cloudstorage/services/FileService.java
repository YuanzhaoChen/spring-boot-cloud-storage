package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.enums.ServiceResponse;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public ServiceResponse addFile(File file) {
        if (file.getUserId()==null){
            return ServiceResponse.USER_NOT_FOUND_ERROR;
        }
        try {
            int rowsAdded = this.fileMapper.insertFile(file);
            if (rowsAdded != 1) {
                return ServiceResponse.DATABASE_ERROR;
            }
            return ServiceResponse.SUCCESS;
        }catch (Exception e){
            return ServiceResponse.DATABASE_ERROR;
        }
    }

    public ServiceResponse deleteFile(Integer id){
        if(this.fileMapper.getFile(id)==null){
            return ServiceResponse.RECORD_NOT_FOUND_ERROR;
        }
        try {
            this.fileMapper.deleteFile(id);
            return ServiceResponse.SUCCESS;
        }catch (Exception e){
            return ServiceResponse.DATABASE_ERROR;
        }
    }

    public File getFile(Integer id){
        return this.fileMapper.getFile(id);
    }

    public File getFile(Integer userId, String fileName){
        return this.fileMapper.getFileByName(userId, fileName);
    }

    public List<File> getAllFiles(Integer userId){
        return this.fileMapper.getAllFiles(userId);
    }
}

