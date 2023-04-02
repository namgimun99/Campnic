package com.lec.spring.service;

import com.lec.spring.domain.ItemFileDTO;
import com.lec.spring.repository.ItemFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemFileService {

    private ItemFileRepository fileRepository;

    @Autowired
    public void setFileRepository(ItemFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public ItemFileService() {
        System.out.println("FileService() 생성");
    }

    public ItemFileDTO findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

}










