package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetailService;
import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
import com.lec.spring.service.CampingService;
import com.lec.spring.service.LenderService;
import com.lec.spring.service.UserService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/camp")
public class CampingController {

        @Autowired
        private CampingService campingService;
        @Autowired
        private CampReserveRepository campReserveRepository;
        @Autowired
        private CampsiteRepository campsiteRepository;
        @Autowired
        private CampingRepository campingRepository;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private CityRepository cityRepository;

        @Autowired
        private LenderService lenderService;

        @Autowired
        private UserService userService;

        @Autowired
        private PrincipalDetailService principalDetailService;

        @GetMapping("/reserveList")
        public void reserveList(Model model) {
                model.addAttribute("list", campingService.myReserve());
        }

        @GetMapping("/reserve")
        public void reserve(Long id, Model model) {

                Camping camping = campingService.campingOne(id);
                model.addAttribute("camping", camping);

                List<Campsite> campsite = campingService.campsiteList(id);
                model.addAttribute("site", campsite);

                User user = U.getLoggedUser();

                // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
                user = userRepository.findById(user.getId()).orElse(null);
                model.addAttribute("name", user);
        }

        @PostMapping("/reserve")
        public String reserveOK(CampReserve campReserve,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

                campReserve.setCoupon(campingService.couponNum());

                model.addAttribute("result", campingService.addReserve(campReserve));
                model.addAttribute("dto", campReserve);

                return "camp/reserveOK";

        }

        @PostMapping("/reserveDelete")
        public String reserveDelete(Long id, Model model) {
                model.addAttribute("result", campingService.campReserveDelete(id));
                return "camp/reserveDelete";
        }

        @GetMapping("/list")
        public void list(Model model) {
                model.addAttribute("list", campingService.campinglist());
                model.addAttribute("city",campingService.citylist());
        }

        @PostMapping("/list")
        public void listOK(
                @RequestParam("cityId") Long cityId
                , Model model
        ) {
                model.addAttribute("list", campingService.searchcamp(cityId));
                model.addAttribute("city",campingService.citylist());

        }

        @GetMapping("/detail")
        public void CampingDetail(Long id, Model model) {
                model.addAttribute("list", campingService.campingDetail(id));
        }

        @GetMapping("/recipt")
        public void recipt(Long id, Model model) {

                model.addAttribute("list", campingService.campReserveDetail(id));

        }

        //  ----------------------------------admin page---------------------------------------------
        @GetMapping("/admin/camping/list")
        public void adminCampingList(Model model) {

                model.addAttribute("list", campingService.myCamping());
        }

        @GetMapping("/admin/camping/detail")
        public void adminCampingDetail(Long id, Model model) {
                model.addAttribute("list", campingService.campingDetail(id));
        }

        @GetMapping("/admin/camping/write")
        public void adminCampingWrite(Model model) {

                model.addAttribute("cityList", lenderService.cityList());
        }

        @PostMapping("/admin/camping/write")
        public String adminCampingWriteOK(@RequestParam Long cityId,
                                          Camping camping,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {
                User num1 = camping.getUser();


                model.addAttribute("result", campingService.addCamping(camping, cityId));
                model.addAttribute("dto", camping);

                return "camp/admin/camping/writeOK";
        }

        @GetMapping("/admin/camping/update")
        public void adminCampingUpdate(Long id, Model model) {
                model.addAttribute("list", campingService.campingDetail(id));
        }

        //
        @PostMapping("/admin/camping/update")
        public String adminCampingUpdateOK(Camping camping,
                                           BindingResult result,
                                           Model model,
                                           RedirectAttributes redirectAttrs) {

                model.addAttribute("result", campingService.campingUpdate(camping));
                model.addAttribute("dto", camping);

                return "/camp/admin/camping/updateOk";

        }

        @PostMapping("/admin/camping/delete")
        public String campingDelete(Long id, Model model) {
                model.addAttribute("result", campingService.campingDelete(id));
                return "camp/admin/camping/deleteOK";
        }
//-----------------------------------campsite--------------------------------------------

        @GetMapping("/admin/campsite/list")
        public void adminCampsiteList(Long id, Model model) {
                model.addAttribute("list", campingService.campsiteList(id));
        }

        @GetMapping("/admin/campsite/write")
        public void adminCampsiteWrite(Long id, Model model) {
                model.addAttribute("camping", campingService.campingOne(id).getId());
        }

        @PostMapping("/admin/campsite/write")
        public String adminCampsiteWriteOK(@RequestParam Map<String, MultipartFile> files,
                                           Campsite campsite,
                                           Model model) {
                Camping num1 = campsite.getCamping();

                model.addAttribute("result", campingService.addCampsite(campsite,files));
                model.addAttribute("dto", campsite);

                return "camp/admin/campsite/writeOK";
        }

        @GetMapping("/admin/campsite/detail")
        public void adminCampsiteDetail(Long id, Model model) {
                model.addAttribute("list", campingService.campsiteDetail(id));
        }

        @GetMapping("/admin/campsite/update")
        public void adminCampsiteUpdate(Long id, Model model) {
                model.addAttribute("list", campingService.campsiteDetail(id));
        }

        @PostMapping("/admin/campsite/update")
        public String adminCampsiteUpdateOK(Campsite campsite,
                                            BindingResult result,
                                            @RequestParam Map<String, MultipartFile> files,     // 새로 추가될 첨부파일들
                                            Long[] delfile,
                                            Model model) {

                model.addAttribute("result", campingService.campsiteUpdate(campsite, files, delfile));
                model.addAttribute("dto", campsite);

                return "camp/admin/campsite/updateOk";
        }

        @PostMapping("/admin/campsite/delete")
        public String campsiteDelete(Long id, Camping camping, Model model) {
                model.addAttribute("result", campingService.campsiteDelete(id));
                Long num1 = camping.getId();
                model.addAttribute("dto", num1);
                return "camp/admin/campsite/deleteOK";
        }

        @GetMapping("/coupon")
        public void coupon(Model model) {
                User user = U.getLoggedUser();

                // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
                user = userRepository.findById(user.getId()).orElse(null);
                model.addAttribute("name", user);
        }

        @Transactional
        @PostMapping("/coupon")
        public String couponOK(Coupon coupon,
                               BindingResult result,
                               Model model,
                                RedirectAttributes redirectAttrs) {

                if(campingService.findByCoupon(coupon.getCpNum())){

                        if(campingService.couponCheck(coupon.getCpNum())) {
                                model.addAttribute("result", campingService.addCoupon(coupon));
                                model.addAttribute("dto", coupon);

                                User user = U.getLoggedUser();

                                // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
                                      user = userRepository.findById(user.getId()).orElse(null);

                                int point = user.getPoint() + 5000;

                                user.setPoint(point);

                                userRepository.saveAndFlush(user);

                                // 덮어씌우기
                                userService.updateUser(user.getId(), point);

                                //수정된 유저정보 principal에 업데이트
                                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                                UserDetails userAccount = (UserDetails) authentication.getPrincipal();
                                SecurityContextHolder.getContext().setAuthentication(
                                        createNewAuthentication(authentication,userAccount.getUsername()));

                                return "camp/couponOK";
                        }else{
                                return "camp/useCoupon";
                        }
                } else {
                        return "camp/noReserve";

                }
        } // end

        protected Authentication createNewAuthentication(Authentication currentAuth, String username) {
                UserDetails newPrincipal = principalDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken newAuth =
                        new UsernamePasswordAuthenticationToken(
                                newPrincipal,
                                currentAuth.getCredentials(),
                                newPrincipal.getAuthorities()
                        );
                newAuth.setDetails(currentAuth.getDetails());
                return newAuth;
        }
}


