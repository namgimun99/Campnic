package com.lec.spring.service;

import com.lec.spring.domain.CampFileDTO;
import com.lec.spring.domain.ItemFileDTO;
import com.lec.spring.repository.CampsiteFileRepository;
import com.lec.spring.repository.ItemFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampsiteFileService {

    private CampsiteFileRepository fileRepository;

    @Autowired
    public void setFileRepository(CampsiteFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public CampsiteFileService() {
        System.out.println("FileService() 생성");
    }

    public CampFileDTO findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

}










