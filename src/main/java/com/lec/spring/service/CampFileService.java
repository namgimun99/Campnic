package com.lec.spring.service;

import com.lec.spring.domain.CampFileDTO;
import com.lec.spring.domain.ItemFileDTO;
import com.lec.spring.repository.CampFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampFileService {

    private CampFileRepository campFileRepository;

    @Autowired
    public void setFileRepository(CampFileRepository campfileRepository) {
        this.campFileRepository = campfileRepository;
    }

    public CampFileService() {
        System.out.println("FileService() 생성");
    }

    public CampFileDTO findById(Long id) {
        return campFileRepository.findById(id).orElse(null);
    }

}










