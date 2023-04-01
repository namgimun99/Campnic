package com.lec.spring.controller;

import com.lec.spring.domain.CampReserve;
import com.lec.spring.domain.Camping;
import com.lec.spring.domain.Campsite;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CampReserveRepository;
import com.lec.spring.repository.CampingRepository;
import com.lec.spring.repository.CampsiteRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.CampingService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

        @GetMapping("/reserve")
        public void reserve(Long id, Model model){

                Camping camping = campingService.campingSave(id);
                model.addAttribute("camping", camping);

                List<Campsite> campsite = campingService.campsiteList(id);
                model.addAttribute("site", campsite);

                User user = U.getLoggedUser();

                // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
                user = userRepository.findById(user.getId()).orElse(null);
                model.addAttribute("name", user);
        }

        @PostMapping("/reserve")
        public String reserveOK(@Valid CampReserve campReserve,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes){

                campReserve.setCoupon(campingService.couponNum());
                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("sdate", campReserve.getSdate());
                        redirectAttributes.addFlashAttribute("edate", campReserve.getEdate());
                        redirectAttributes.addFlashAttribute("campsite", campReserve.getCampsite().getId());

                        List<FieldError> errList = result.getFieldErrors();

                        for(FieldError err : errList){
                                redirectAttributes.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/reserve";
                }

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
        public void adminCampingWrite(Camping camping, Model model) {

                model.addAttribute("list", camping);

        }

        @PostMapping("/admin/camping/write")
        public String adminCampingWriteOK(@RequestParam Map<String, MultipartFile> files,
                                          @Valid Camping camping,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttributes){
                        User num1 = camping.getUser();

                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("camp_name", camping.getCamp_name());
                        redirectAttributes.addFlashAttribute("address", camping.getAddress());
                        redirectAttributes.addFlashAttribute("content", camping.getContent());
                        redirectAttributes.addFlashAttribute("city",camping.getCity().getCity());


                        List<FieldError> errList = result.getFieldErrors();

                        for(FieldError err : errList){
                                redirectAttributes.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/admin/camping/write?user=" + num1;
                }
                model.addAttribute("result", campingService.addCamping(camping));
                model.addAttribute("dto", camping);

                return "camp/admin/camping/writeOK";
        }

        @GetMapping("/admin/camping/update")
        public void adminCampingUpdate(Long id, Model model) {
            model.addAttribute("list", campingService.campingDetail(id));
        }
//
        @PostMapping("/admin/camping/update")
        public String adminCampingUpdateOK(@Valid Camping camping,
                                         BindingResult result,
                                         Model model,
                                         RedirectAttributes redirectAttrs){
                if (result.hasErrors()) {
                        redirectAttrs.addFlashAttribute("campname", camping.getCamp_name());
                        redirectAttrs.addFlashAttribute("content", camping.getContent());
                        redirectAttrs.addFlashAttribute("address", camping.getAddress());

                        List<FieldError> errList = result.getFieldErrors();
                        for (FieldError err : errList) {
                                redirectAttrs.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/admin/camping/update?id=" + camping.getId();
                }

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
        public void adminCampsiteList(Long id, Model model){
            model.addAttribute("list", campingService.campsiteList(id));
        }

        @GetMapping("/admin/campsite/write")
        public void adminCampsiteWrite(Campsite campsite, Long id, Model model){
                model.addAttribute("list", campsite);

                Camping camping = campingService.campingSave(id);
                model.addAttribute("camping", camping);
        }

        @PostMapping("/admin/campsite/write")
        public String adminCampsiteWriteOK(@RequestParam Map<String, MultipartFile> files,
                                         @Valid Campsite campsite,
                                         BindingResult result,
                                         Model model,
                                         RedirectAttributes redirectAttributes){
                Camping num1 = campsite.getCamping();

                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("number", campsite.getNumber());
                        redirectAttributes.addFlashAttribute("price", campsite.getPrice());
                        redirectAttributes.addFlashAttribute("content", campsite.getContent());
                        redirectAttributes.addFlashAttribute("camping", campsite.getCamping().getId());


                        List<FieldError> errList = result.getFieldErrors();

                        for(FieldError err : errList){
                                redirectAttributes.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/admin/campsite/Write?camping_id="+num1;
                }
                model.addAttribute("result", campingService.addCampsite(campsite));
                model.addAttribute("dto", campsite);

                return "camp/admin/campsite/writeOK";
        }

        @GetMapping("/admin/campsite/detail")
        public void adminCampsiteDetail(Long id, Model model){
            model.addAttribute("list", campingService.campsiteDetail(id));
        }

        @GetMapping("/admin/campsite/update")
        public void adminCampsiteUpdate(Long id, Model model){
            model.addAttribute("list", campingService.campsiteDetail(id));
        }

        @PostMapping("/admin/campsite/update")
        public String adminCampsiteUpdateOK(@Valid Campsite campsite,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttrs){
                if (result.hasErrors()) {
                        redirectAttrs.addFlashAttribute("number", campsite.getNumber());
                        redirectAttrs.addFlashAttribute("content", campsite.getContent());
                        redirectAttrs.addFlashAttribute("price", campsite.getPrice());

                        List<FieldError> errList = result.getFieldErrors();
                        for (FieldError err : errList) {
                                redirectAttrs.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/admin/campsite/update?id=" + campsite.getId();
                }

                model.addAttribute("result", campingService.campsiteUpdate(campsite));
                model.addAttribute("dto", campsite);

                return "camp/admin/campsite/updateOk";
        }
        @PostMapping("/admin/campsite/delete")
       public String campsiteDelete(Long id,Camping camping, Model model) {
                model.addAttribute("result", campingService.campsiteDelete(id));
                Long num1 =camping.getId();
                model.addAttribute("dto",num1);
                return "camp/admin/campsite/deleteOK";
        }
        }

