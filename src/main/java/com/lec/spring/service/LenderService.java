package com.lec.spring.service;

import com.lec.spring.domain.City;
import com.lec.spring.domain.Lender;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CityRepository;
import com.lec.spring.repository.LenderRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Service layer
// - Transaction 담당

@Service
public class LenderService {

    private LenderRepository lenderRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;

    @Autowired
    public void setWriteRepository(LenderRepository lenderRepository) {
        this.lenderRepository = lenderRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public LenderService() {
        System.out.println("LenderService() 생성");
    }

    // 보류
//    public List<String> getCityList() {
//        List<String> cityList = new ArrayList<>();
//        List<City> city = cityRepository.findAll();
//        for (City i : city) {
//            cityList.add(i.getCity());
//        }
//        return cityList;
//    }

    public List<City> cityList() {
        List<City> cities = cityRepository.findAll();
        return cities;
    }

    // admin city 등록
    @Transactional
    public int addCity(City city) {
        for (City i : cityList()) {
            if (city.equals(i.getCity())) return 0;
        }
        city = cityRepository.saveAndFlush(city);
        return 1;
    }

    // admin city 삭제
    // 이주석은 지우시길, hasAuthorized button 쓰세요
    public int delCity(Long id){
        City city = cityRepository.findById(id).orElse(null);
        if(city != null){
            cityRepository.delete(city);
            return 1;
        }
        return 0;
    }

    // lender 등록
    public int addLender(String lender_name, String address, City city) {
        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();
        Lender lender = new Lender();

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        lender.setUser(user);  // 글 작성자 세팅
        lender.setLender_name(lender_name);
        lender.setAddress(address);
        lender.setCity(city);

        lender = lenderRepository.saveAndFlush(lender);  // INSERT

        return 1;
    }

    // lenderAdmin 렌더 목록
    public List<Lender> lenderList() {
        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();
        List<Lender> lenderList = lenderRepository.findByUserOrderByIdDesc(user);
        return lenderList;
    }

    // lenderAdmin 렌더 수정
    public int lenderUpdate(Lender lender){
        // update 하고자 하는 것을 일단 읽어와야 한다
        Lender w = lenderRepository.findById(lender.getId()).orElse(null);
        if(w != null){
            w.setAddress(lender.getAddress());
            w.setCity(lender.getCity());
            w.setLender_name(lender.getLender_name());
            lenderRepository.save(w);   // UPDATE
            return 1;
        }
        return 0;
    } // end update

    // lenderAdmin 렌더 삭제
    public int deleteLender(Long id) {
        Lender write = lenderRepository.findById(id).orElse(null);
        if (write != null) {
            // 글삭제 (참조하는 첨부파일, 댓글 등도 같이 삭제 될 것이다 ON DELETE CASCADE)
            lenderRepository.delete(write);
            return 1;
        }
        return 0;
    }

}







