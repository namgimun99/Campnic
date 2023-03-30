package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
import com.lec.spring.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    private final ItemRepository itemRepository;
    private final RentalReciptRepository rentalReciptRepository;

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

    public LenderService(ItemRepository itemRepository,
                         RentalReciptRepository rentalReciptRepository) {
        System.out.println("LenderService() 생성");
        this.itemRepository = itemRepository;
        this.rentalReciptRepository = rentalReciptRepository;
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
    @Transactional
    public int addLender(Lender lender, Long cityId) {

        String lenderName = lender.getLenderName();
        City c = cityRepository.findById(cityId).orElse(null);

        for (Lender i : lenderList()) {
            if (lenderName.equals(i.getLenderName())) return 0;
        }
        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        lender.setUser(user);  // 글 작성자 세팅
        lender.setCity(c);

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

    // lenderAdmin 렌더 하나
    public Lender lender(Long id) {
        return lenderRepository.findById(id).orElse(new Lender());
    }

    // lenderAdmin 렌더 수정
    @Transactional
    public int lenderUpdate(Lender lender, Long cityId){ // city 수정가능
        // update 하고자 하는 것을 일단 읽어와야 한다
        Lender w = lenderRepository.findById(lender.getId()).orElse(null);
        City c = cityRepository.findById(cityId).orElse(null);
        if(w != null){
            w.setCity(c);
            w.setAddress(lender.getAddress());
            w.setLenderName(lender.getLenderName());
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

    // lenderAdmin Item 등록
    // TODO item file
    @Transactional
    public int addItem(Item item, String lenderName) {
        Lender lender = lenderRepository.findByLenderName(lenderName);
        item.setLender(lender);
        item = itemRepository.saveAndFlush(item);
        return 1;
    }

    // lenderAdmin Item 수정
    public int updateItem(Item item) { // lenderName 수정불가
        // update 하고자 하는 것을 일단 읽어와야 한다
        Item w = itemRepository.findById(item.getId()).orElse(null);
        if(w != null){
            w.setContent(item.getContent());
            w.setPrice(item.getPrice());
            w.setItemName(item.getItemName());
            itemRepository.save(w);   // UPDATE
            return 1;
        }
        return 0;
    }

    // lenderAdmin Item 삭제
    public int deleteItem(long id){
        Item w = itemRepository.findById(id).orElse(null);
        if(w != null){
            // 글삭제 (참조하는 첨부파일, 댓글 등도 같이 삭제 될 것이다 ON DELETE CASCADE)
            itemRepository.delete(w);
            return 1;
        }
        return 0;
    }

    // lenderAdmin Item 목록
    public List<Item> myItemList(String lenderName) {
        Lender lender = lenderRepository.findByLenderName(lenderName);
        List<Item> itemList  = itemRepository.findByLender(lender);
        return itemList;
    }

    // Item 목록
    public List<Item> itemList(){
        List<Item> itemList = itemRepository.findAll();
        return itemList;
    }

    // 지역 Item 목록
    @Transactional
    public List<Item> searchItemList(String cityName){
        City city = cityRepository.findByCity(cityName);
        List<Lender> lenderList = lenderRepository.findByCity(city);
        List<Item> itemList = new ArrayList<>();
        for(Lender l : lenderList){
            if(cityName.equals(l.getCity().getCity())) {
                List<Item> items = itemRepository.findByLender(l);
                itemList.addAll(items);
            }
        }
        return itemList;
    }


    // Item detail
    public Item itemDetail(Long id){
        Item item = itemRepository.findById(id).orElse(null);
        return item;
    }

    // rental 하기
    public int addRental(RentalRecipt rentalRecipt, Long item_id){ // item_id hidden
        Item item = itemRepository.findById(item_id).orElse(null);
        if(item != null){
            // 현재 로그인한 작성자 정보
            User user = U.getLoggedUser();

            // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
            user = userRepository.findById(user.getId()).orElse(null);
            rentalRecipt.setUser(user);
            rentalRecipt.setItem(item);
            rentalRecipt = rentalReciptRepository.saveAndFlush(rentalRecipt);
            return 1;
        }
        return 0;
    }

    // 취소일이 렌트 당일인지 check
    public boolean checkDays(RentalRecipt rentalRecipt){
        LocalDate sdate = rentalRecipt.getSdate();
        if(sdate != LocalDate.now()) {return true;}
        return false;
    }

    // rental 취소
    public int delRental(Long id){
        RentalRecipt rentalRecipt = rentalReciptRepository.findById(id).orElse(null);
        if(rentalRecipt != null && checkDays(rentalRecipt)){
            rentalReciptRepository.delete(rentalRecipt);
            return 1;
        }
        return 0;
    }

    // 내 rental 목록
    public List<RentalRecipt> myRental() {
        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();
        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        List<RentalRecipt> myRental = rentalReciptRepository.findByUserOrderByIdDesc(user);
        return myRental;
    }
}







