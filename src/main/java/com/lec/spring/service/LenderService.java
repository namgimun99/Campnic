package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
import com.lec.spring.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Service layer
// - Transaction 담당

@Service
public class LenderService {

    @Value("${app.item.upload.path}")
    private String uploadDir;

    private LenderRepository lenderRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private final ItemRepository itemRepository;
    private final RentalReciptRepository rentalReciptRepository;
    private ItemFileRepository itemFileRepository;

    @Autowired
    public void setItemFileRepository(ItemFileRepository itemFileRepository) {
        this.itemFileRepository = itemFileRepository;
    }

    @Autowired
    public void setLenderRepository(LenderRepository lenderRepository) {
        this.lenderRepository = lenderRepository;
    }

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
    @Transactional
    public int addItem(
            Item item
            , Long lenderId
            , Map<String, MultipartFile> files   // 첨부 파일들.
            ) {
        Lender lender = lenderRepository.findById(lenderId).orElse(null);
        if (lender != null){
            item.setLender(lender);
            item = itemRepository.saveAndFlush(item);
            // 첨부파일 추가
            addFiles(files, item.getId());
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
                ItemFileDTO file = upload(e.getValue());

                // 성공하면 DB 에도 저장
                if(file != null){
                    file.setItem(id);   // FK 설정
                    itemFileRepository.save(file);   // INSERT
                }
            }
        }
    }// end addFiles()

    // 물리적으로 파일 저장.  중복된 이름 rename 처리
    private ItemFileDTO upload(MultipartFile multipartFile){
        ItemFileDTO attachment = null;

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

        attachment = ItemFileDTO.builder()
                .file(fileName)   // 저장된 이름
                .source(sourceName)  // 원본이름
                .build();

        return attachment;
    } // end upload

    // lenderAdmin Item 수정
    public int updateItem(
            Item item
            , Map<String, MultipartFile> files   // 새로 추가된 첨부파일들
            , Long[] delfile){   // 삭제될 첨부파일들
        // update 하고자 하는 것을 일단 읽어와야 한다
        Item w = itemRepository.findById(item.getId()).orElse(null);
        if(w != null){
            w.setContent(item.getContent());
            w.setPrice(item.getPrice());
            w.setItemName(item.getItemName());
            itemRepository.save(w);   // UPDATE

            // 첨부파일 추가
            addFiles(files, item.getId());

            // 삭제할 첨부파일들은 삭제하기
            if(delfile != null){
                for(Long fileId : delfile){
                    ItemFileDTO file = itemFileRepository.findById(fileId).orElse(null);
                    if(file != null){
                        delFile(file);   // 물리적으로 삭제
                        itemFileRepository.delete(file);  // dB 에서 삭제
                    }
                }
            }
            return 1;
        }
        return 0;
    }

    // 특정 첨부파일(id) 를 물리적으로 삭제
    private void delFile(ItemFileDTO file) {
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

    // lenderAdmin Item 삭제
    public int deleteItem(long id){
        Item w = itemRepository.findById(id).orElse(null);
        if(w != null){
            // 물리적으로 저장된 첨부파일(들) 삭제
            List<ItemFileDTO> fileList = itemFileRepository.findByItem(id);
            if(fileList != null && fileList.size() > 0) {
                for(ItemFileDTO file : fileList) {
                    delFile(file);
                }
            }

            // 글삭제 (참조하는 첨부파일, 댓글 등도 같이 삭제 될 것이다 ON DELETE CASCADE)
            itemRepository.delete(w);
            return 1;
        }
        return 0;
    }

    // lenderAdmin Item 목록
    public List<Item> myItemList(Long lenderId) {
        Lender lender = lenderRepository.findById(lenderId).orElse(null);
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
        if(item != null){
            // 첨부파일(들) 정보 가져오기
            List<ItemFileDTO> fileList = itemFileRepository.findByItem(item.getId());
            setImage(fileList);// 이미지 파일 여부 세팅
        }
        return item;
    }

    // [이미지 파일 여부 세팅]
    private void setImage(List<ItemFileDTO> fileList) {
        // upload 실제 물리적인 경로
        String realPath = new File(uploadDir).getAbsolutePath();

        for(ItemFileDTO fileDto : fileList) {
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







