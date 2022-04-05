package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles() {
        return fileMapper.getFiles();
    }

    public List<File> getFilesByUserid(Integer userid) {
        return fileMapper.getFileByUserid(userid);
    }

    public void addFile(File file) {
        fileMapper.insert(file);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public int deleteFileById(Integer fileId) {
        return fileMapper.deleteFileByFileId(fileId);
    }
}
