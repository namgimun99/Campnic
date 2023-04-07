package com.lec.spring.service;


import com.lec.spring.common.C;
import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class CampingService {

    @Value("${app.camp.upload.path}")
    private String uploadDir;
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
    public int addCamping(Camping camping, Long cityId) {

        // 현재 로그인한 작성자 정보
        User user = U.getLoggedUser();
        City city = cityRepository.findById(cityId).orElse(null);

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getId()).orElse(null);
        camping.setUser(user);  // 글 작성자 세팅
        camping.setCity(city);

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

    public Camping campingOne(Long id) {
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
            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(campsite.getId());
            setImage(fileList);
            campsite.setFileList(fileList);
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
    public int campsiteUpdate(Campsite campsite,
                              Map<String, MultipartFile> files,
                              Long[] delfile
    ) {
        int result = 0;

        //update 하고자 하는 것을 일단 읽어와야 한다.
        Campsite campst = campsiteRepository.findById(campsite.getId()).orElse(null);
        if(campst !=null){
            campst.setNumber(campsite.getNumber());
            campst.setPrice(campsite.getPrice());
            campst.setContent(campsite.getContent());

            campsiteRepository.save(campst); // Update

            // 첨부파일 추가
            addFiles(files, campsite.getId());

            // 삭제할 첨부파일들은 삭제하기
            if(delfile != null){
                for(Long fileId : delfile){
                    CampFileDTO file = campsiteFileRepository.findById(fileId).orElse(null);
                    if(file != null){
                        delFile(file);   // 물리적으로 삭제
                        campsiteFileRepository.delete(file);  // dB 에서 삭제
                    }
                }
            }
            result = 1;
        }

        return result;
    }
    private void delFile(CampFileDTO file) {
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, file.getFile()); // 물리적으로 저장된 파일들이 삭제 대상
        System.out.println("삭제시도--> " + f.getAbsolutePath());

        if (f.exists()) {
            if (f.delete()) { // 삭제!
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        } // end if
    }

    private void setImage(List<CampFileDTO> fileList) {
        // upload 실제 물리적인 경로
        String realPath = new File(uploadDir).getAbsolutePath();

        for(CampFileDTO fileDto : fileList) {
            BufferedImage imgData = null;
            File f = new File(realPath, fileDto.getFile());  // 첨부파일에 대한 File 객체
            try {
                imgData = ImageIO.read(f);
                // ※ ↑ 파일이 존재 하지 않으면 IOExcepion 발생한다
                //   ↑ 이미지가 아닌 경우는 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }

            if(imgData != null) fileDto.setImage(true); // 이미지 여부 체크
        } // end for
    }


    //특정 글(id) 삭제
    public int campsiteDelete(Long id) {
        int result = 0;

        Campsite campsite = campsiteRepository.findById(id).orElse(null);
        if (campsite != null) {
            // 물리적으로 저장된 첨부파일(들) 삭제
            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(id);
            if (fileList != null && fileList.size() > 0) {
                for (CampFileDTO file : fileList) {
                    delFile(file);
                }
            }

            campsiteRepository.delete(campsite);
            result = 1;
        }
        return result;
    }


    @Transactional
    public int addCampsite(Campsite campsite,
                           Map<String, MultipartFile> files) {

        campsite = campsiteRepository.save(campsite);  // INSERT
        if(campsite != null){
            addFiles(files, campsite.getId());
            return 1;
        }
        return 0;
    }


    // 특정 글(id) 첨부파일(들) 추가
    private void addFiles(Map<String, MultipartFile> files, Long id){
        if(files != null){
            for(Map.Entry<String, MultipartFile> e :files.entrySet()){

                // name="upfile##" 인 경우만 첨부파일 등록. (이유, 다른 웹에디터와 섞이지 않도록..ex: summernote)
                if(!e.getKey().startsWith("upfile")) continue;

                // 첨부파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey());   // name값
                U.printFileInfo(e.getValue());
                System.out.println();

                // 물리적인 파일 저장
                CampFileDTO file = upload(e.getValue());

                // 성공하면 DB 에도 저장
                if(file != null){
                    file.setCampsite(id);   // FK 설정
                    campsiteFileRepository.save(file);   // INSERT
                }
            }
        }
    }// end addFiles()

    // 물리적으로 파일 저장.  중복된 이름 rename 처리
    private CampFileDTO upload(MultipartFile multipartFile){
        CampFileDTO attachment = null;

        // 담긴 파일이 없으면 pass~
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        // 원본 파일 명
        //                  org.springframework.util.StringUtils
        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // 저장될 파일 명
        String fileName = sourceName;

        // 파일이 중복되는지 확인.
        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){   // 이미 존재하는 파일명,  중복된다면 다름 이름은 변경하여 파일 저장
            int pos = fileName.lastIndexOf(".");
            if(pos > -1) { // 확장자가 있는 경우
                String name = fileName.substring(0, pos);  // 파일'이름'
                String ext = fileName.substring(pos + 1);  // 파일'확장명'

                // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

        // 저장할 피일명
        System.out.println("fileName: " + fileName);

        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다

            // java.nio.file.Files
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING   // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            e.printStackTrace();
            // 예외처리는 여기서.
            //throw new FileStorageException("Could not store file : " + multipartFile.getOriginalFilename());
        }

        attachment = CampFileDTO.builder()
                .file(fileName)   // 저장된 이름
                .source(sourceName)  // 원본이름
                .build();

        return attachment;
    } // end upload

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
            List<CampFileDTO> fileList = campsiteFileRepository.findByCampsite(campReserve.getCampsite().getId());
            setImage(fileList);
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
        List<CampReserve> myReserve = campReserveRepository.findByUserOrderByIdAsc(user);
        return myReserve;
    }

    public CampReserve reserveOne(Long id){
        CampReserve camp = campReserveRepository.findById(id).orElse(null);
        return camp;
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

    public boolean findByCoupon(String coupon) {
        boolean result = false;
        CampReserve campReserve = campReserveRepository.findByCoupon(coupon);
        if(campReserve != null){
                result = true;
        }
        return result;
    }

    public boolean couponCheck(String coupon){
        boolean result = false;
        Coupon couponCheck = couponRepository.findByCpNum(coupon);
            if(couponCheck == null) {
                result = true;
            }
            return result;
        }

    @Transactional
    public List<Camping> searchcamp(Long cityId){
        City city = cityRepository.findById(cityId).orElse(new City());
        List<Camping> campList = campingRepository.findByCity(city);

        return campList;
    }
}