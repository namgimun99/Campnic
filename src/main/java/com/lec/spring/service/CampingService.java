package com.lec.spring.service;


import com.lec.spring.common.C;
import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class CampingService {
    private CampingRepository campingRepository;
    private AuthorityRepository authorityRepository;

    private CampsiteRepository campsiteRepository;
    private final CampsiteFileRepository campsiteFileRepository;

    private CampReserveRepository campReserveRepository;

    private  UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public void setCampingRepository(CampingRepository campingRepository) {
        this.campingRepository = campingRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCampsiteRepository(CampsiteRepository campsiteRepository) {
        this.campsiteRepository = campsiteRepository;
    }

    @Autowired
    public void setCampReserveRepository(CampReserveRepository campReserveRepository) {
        this.campReserveRepository = campReserveRepository;
    }

    public CampingService(CampsiteFileRepository campsiteFileRepository,
                          CityRepository cityRepository,
                          CouponRepository couponRepository) {
        System.out.println("CampingService() 생성");

        this.campsiteFileRepository = campsiteFileRepository;
        this.cityRepository = cityRepository;
        this.couponRepository = couponRepository;
    }

    //전체 리스트 불러오기
    public List<Camping> campinglist() {
        return campingRepository.findAll();
    }

    public List<City> citylist(){ return cityRepository.findAll();}

    public List<CampReserve> campReserveslist(){return campReserveRepository.findAll();}


    //camping 등록
    public int addCamping(Camping camping) {

        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();


        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        camping.setUser(user);  // 글 작성자 세팅

        camping = campingRepository.save(camping);  // INSERT

        return 1;
    }

    //페이징
    public List<Camping> CampingList(Integer page, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer campingPages = (Integer) session.getAttribute("campingPages");
        if (campingPages == null) campingPages = C.WRITE_PAGES;
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if (pageRows == null) pageRows = C.PAGE_ROWS;
        session.setAttribute("page", page);

        Page<Camping> pageWrites = campingRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if (page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = ((int) ((page - 1) / campingPages) * campingPages) + 1;
        int endPage = startPage + campingPages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("campingPages", campingPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<Camping> list = pageWrites.getContent();
        model.addAttribute("list", list);


        return list;
    } // 페이징 end

    //CampingDetail
    @Transactional
    public List<Camping> campingDetail(Long id) {
        List<Camping> list = new ArrayList<>();

        Camping camping = campingRepository.findById(id).orElse(null);
        if (camping != null) {
//            첨부파일 정보 가져오기 ??
//            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(camping.getId());
//            setImage(fileList);
//            camping.setFileList(fileList);
            list.add(camping);
        }
        return list;
    }

    public Camping campingSave(Long id) {
        Camping camping = campingRepository.findById(id).orElse(null);
        return camping;
    }

    //Camping update
    public int campingUpdate(Camping camping) {
        int result = 0;

        //update 하고자 하는 것을 일단 읽어와야 한다.
        Camping camp = campingRepository.findById(camping.getId()).orElse(null);
        if(camping !=null){
            camp.setCamp_name(camping.getCamp_name());
            camp.setAddress(camping.getAddress());
            camp.setContent(camping.getContent());
            camp.setCity(camping.getCity());

            campingRepository.save(camp); // Update
            result = 1;
        }

        return result;
    }

    //Camping 특정 글(id) 삭제
    public int campingDelete(Long id){
        int result = 0;

        Camping camping = campingRepository.findById(id).orElse(null);
        if(camping !=null){
            campingRepository.delete(camping);
            result = 1;
        }
        return result;
    }

    //등록한 camping 목록 보기
    public List<Camping> myCamping(){
        User user = U.getLoggedUser();
        List<Camping> myCamping = campingRepository.findByUserOrderByIdAsc(user);
        return myCamping;
    }
    //------------------------------------------------------------
    //------------------------------------------------------------

    //campsite

    //camping에 등록된 campsite 목록
    public List<Campsite> campsiteList(Long id){

        List<Campsite> campsiteList = campsiteRepository.findByCampingIdOrderByIdAsc(id);
        return campsiteList;
    }
    public List<Campsite> list() {
        return campsiteRepository.findAll();
    }

    //campsite Detail
    @Transactional
    public List<Campsite> campsiteDetail(Long id) {
        List<Campsite> list = new ArrayList<>();

        Campsite campsite = campsiteRepository.findById(id).orElse(null);
        if (campsite != null) {
//            첨부파일 정보 가져오기 ??
//            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(camping.getId());
//            setImage(fileList);
//            camping.setFileList(fileList);
            list.add(campsite);
        }
        return list;
    }

    //페이징
    public List<Campsite> list(Integer page, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer campsitePages = (Integer) session.getAttribute("campsitePages");
        if (campsitePages == null) campsitePages = C.WRITE_PAGES;
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if (pageRows == null) pageRows = C.PAGE_ROWS;
        session.setAttribute("page", page);

        Page<Campsite> pageWrites = campsiteRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if (page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = ((int) ((page - 1) / campsitePages) * campsitePages) + 1;
        int endPage = startPage + campsitePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("campsitePages", campsitePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<Campsite> list = pageWrites.getContent();
        model.addAttribute("list", list);


        return list;
    } // 페이징 end

    //특정 글(id) update
    public int campsiteUpdate(Campsite campsite
    ) {
        int result = 0;

        //update 하고자 하는 것을 일단 읽어와야 한다.
        Campsite campst = campsiteRepository.findById(campsite.getId()).orElse(null);
        if(campst !=null){
            campst.setNumber(campsite.getNumber());
            campst.setPrice(campsite.getPrice());
            campst.setContent(campsite.getContent());

            campsiteRepository.save(campst); // Update
            result = 1;
        }

        return result;
    }

    //특정 글(id) 삭제
    public int campsiteDelete(Long id){
        int result = 0;

        Campsite campsite = campsiteRepository.findById(id).orElse(null);
        if(campsite !=null){
            campsiteRepository.delete(campsite);
            result = 1;
        }
        return result;
    }


    public int addCampsite(Campsite campsite) {

        campsite = campsiteRepository.save(campsite);  // INSERT

        return 1;
    }

//------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------
    //CampReserve

    //CampReserve 추가
public int addReserve(CampReserve campReserve) {
    // 현재 로그인한 작성자 정보
    User user = U.getLoggedUser();

    // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
    user = userRepository.findById(user.getId()).orElse(null);
    campReserve.setUser(user);  // 글 작성자 세팅

    campReserve = campReserveRepository.save(campReserve);  // INSERT

    return 1;
}

    //페이징
    public List<CampReserve> CampReserveList(Integer page, Model model) {
        if (page == null) page = 1;
        if (page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer CampReservePages = (Integer) session.getAttribute("CampReservePages");
        if (CampReservePages == null) CampReservePages = C.WRITE_PAGES;
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if (pageRows == null) pageRows = C.PAGE_ROWS;
        session.setAttribute("page", page);

        Page<CampReserve> pageWrites = campReserveRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if (page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = ((int) ((page - 1) / CampReservePages) * CampReservePages) + 1;
        int endPage = startPage + CampReservePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("CampReservePages", CampReservePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<CampReserve> list = pageWrites.getContent();
        model.addAttribute("list", list);


        return list;
    } // 페이징 end

    //CampReserveDetail
    @Transactional
    public List<CampReserve> campReserveDetail(Long id) {
        List<CampReserve> list = new ArrayList<>();

        CampReserve campReserve = campReserveRepository.findById(id).orElse(null);
        if (campReserve != null) {
//            첨부파일 정보 가져오기 ??
//            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(camping.getId());
//            setImage(fileList);
//            camping.setFileList(fileList);
            list.add(campReserve);
        }
        return list;
    }

    //campReserve update
    public int campReserveUpdate(CampReserve campReserve) {
        int result = 0;

        //update 하고자 하는 것을 일단 읽어와야 한다.
        CampReserve reserve = campReserveRepository.findById(campReserve.getId()).orElse(null);
        if(reserve !=null){
            reserve.setCampsite(reserve.getCampsite());
            reserve.setSdate(reserve.getSdate());
            reserve.setEdate(reserve.getEdate());

            campReserveRepository.save(reserve); // Update
            result = 1;
        }

        return result;
    }

    //campReserve 특정 글(id) 삭제
    public int campReserveDelete(Long id){
        int result = 0;

        CampReserve campReserve = campReserveRepository.findById(id).orElse(null);
        if(campReserve !=null){
            campReserveRepository.delete(campReserve);
            result = 1;
        }
        return result;
    }
// 등록한 예약 목록 보기
    public List<CampReserve> myReserve(){
        User user = U.getLoggedUser();
        List<CampReserve> myReserve = campReserveRepository.findByUserOrderByIdDesc(user);
        return myReserve;
    }



//    public boolean isExist(int sdate){
//        CampReserve campReserve = findBySdate(sdate);
//        return (campReserve != null) ? true : false;
//    }

//------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------

    //coupon 생성
    public int addCoupon(Coupon coupon) {
        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        coupon.setUser(user);  // 글 작성자 세팅

        coupon = couponRepository.save(coupon);  // INSERT

        return 1;

    }

    public String couponNum() {
            // 8자리의 랜덤 난수 생성
            Random random = new Random();
            long Num = (long) (random.nextDouble() * 100000000L);
            String couponNum = String.format("%08d", Num);

        return couponNum;
    }

}