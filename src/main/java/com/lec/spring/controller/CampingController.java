package com.lec.spring.controller;

import com.lec.spring.domain.CampReserve;
import com.lec.spring.domain.Camping;
import com.lec.spring.domain.Campsite;
import com.lec.spring.repository.CampReserveRepository;
import com.lec.spring.service.CampingService;
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

        @GetMapping("/reserve")
        public void reserve(long UserId, Model model){
                model.addAttribute("userId", UserId);
        }


        @PostMapping("/reserve")
        public String reserveOK( @RequestParam Map<String, MultipartFile> files,
                               @Valid CampReserve campReserve,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes){

                Long num1 = campReserve.getCampsite().getCamping().getId();
                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("sdate", campReserve.getSdate());
                        redirectAttributes.addFlashAttribute("edate", campReserve.getEdate());
                        redirectAttributes.addFlashAttribute("campsite", campReserve.getCampsite());

                        List<FieldError> errList = result.getFieldErrors();

                        for(FieldError err : errList){
                                redirectAttributes.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/reserve?camping_id="+num1;
                }
                model.addAttribute("result", campingService.addReserve(campReserve));
                model.addAttribute("dto", campReserve);
                model.addAttribute("list",campingService.campsiteList(campReserve.getCampsite().getCamping().getId()));

                campReserve.setCoupon(campingService.couponNum());

                return "camp/reserveOK";

        }

        @PostMapping("/reserveDelete")
        public String reserveDelete(long id, Model model) {
            model.addAttribute("result", campingService.campReserveDelete(id));
            return "camp/reserveDelete";
        }

//        @GetMapping("/update")
//        public void reserveUpdate(long id, Model model) {
//                model.addAttribute("list", campingService.campReserveDetail(id));
//        }
//
//        @PostMapping("/update")
//        public String updateOk(@Valid CampReserve campReserve,
//                               BindingResult result,
//                               Model model,
//                               RedirectAttributes redirectAttrs) {
//                // validation
//                if (result.hasErrors()) {
//
//                        redirectAttrs.addFlashAttribute("sdate", campReserve.getSdate());
//                        redirectAttrs.addFlashAttribute("edate", campReserve.getEdate());
//                        redirectAttrs.addFlashAttribute("campsite", campReserve.getCampsite());
//
//                        List<FieldError> errList = result.getFieldErrors();
//                        for (FieldError err : errList) {
//                                redirectAttrs.addFlashAttribute("error", err.getCode());
//                                break;
//                        }
//                        return "redirect:/camp/update?id=" + campReserve.getId();
//                }
//                model.addAttribute("result", campingService.campReserveUpdate(campReserve));
//                model.addAttribute("dto", campReserve);
//
//                return "camp/updateOk";
//        }


        @GetMapping("/list")
        public void list(Model model) {
            model.addAttribute("list", campingService.campinglist());
        }

        @GetMapping("/Detail")
        public void CampingDetail(long id, Model model) {
            model.addAttribute("list", campingService.campingDetail(id));
        }

       @GetMapping("/recipt")
       public void recipt(long id, Model model) {
           model.addAttribute("list", campingService.campReserveDetail(id));
       }

//  ----------------------------------admin page---------------------------------------------
        @GetMapping("/admin/camping/list")
        public void adminCampingList(Model model) {
            model.addAttribute("list", campingService.myCamping());
        }
        @GetMapping("/admin/camping/detail")
        public void adminCampingDetail(long id, Model model) {
            model.addAttribute("list", campingService.campingDetail(id));
        }

        @GetMapping("/admin/camping/write")
        public void adminCampingWrite(long UserId, Model model) {
            model.addAttribute("userId", UserId);
        }

        @PostMapping("/admin/camping/write")
        public String adminCampingWriteOK(@RequestParam Map<String, MultipartFile> files,
                                          @Valid Camping camping,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttributes){

                Long num1 = camping.getUser().getId();

                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("campName", camping.getCamp_name());
                        redirectAttributes.addFlashAttribute("address", camping.getAddress());
                        redirectAttributes.addFlashAttribute("content", camping.getContent());
                        redirectAttributes.addFlashAttribute("city",camping.getCity());


                        List<FieldError> errList = result.getFieldErrors();

                        for(FieldError err : errList){
                                redirectAttributes.addFlashAttribute("error", err.getCode());
                                break;
                        }
                        return "redirect:/camp/admin/camping/write?user_id="+num1;
                }
                model.addAttribute("result", campingService.addCamping(camping));
                model.addAttribute("dto", camping);

                return "camp/admin/camping/writeOK";
        }

        @GetMapping("/admin/camping/update")
        public void adminCampingUpdate(long id, Model model) {
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
                        redirectAttrs.addFlashAttribute("city", camping.getCity());
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
        public String campingDelete(long id, Model model) {
                model.addAttribute("result", campingService.campingDelete(id));
                return "camp/admin/camping/delete";
        }
//-----------------------------------campsite--------------------------------------------

        @GetMapping("/admin/campsite/list")
        public void adminCampsiteList(long id, Model model){
            model.addAttribute(("list"), campingService.campsiteList(id));
        }

        @GetMapping("/admin/campsite/write")
        public void adminCampsiteWrite(long CampingId, Model model){
                            model.addAttribute("campingId", CampingId);
        }

        @PostMapping("/admin/campsite/write")
        public String adminCampsiteWriteOK(@RequestParam Map<String, MultipartFile> files,
                                         @Valid Campsite campsite,
                                         BindingResult result,
                                         Model model,
                                         RedirectAttributes redirectAttributes){
                Long num1 = campsite.getCamping().getId();

                if(result.hasErrors()){
                        redirectAttributes.addFlashAttribute("number", campsite.getNumber());
                        redirectAttributes.addFlashAttribute("price", campsite.getPrice());
                        redirectAttributes.addFlashAttribute("content", campsite.getContent());


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
        public void adminCampsiteDetail(long id, Model model){
            model.addAttribute("list", campingService.campsiteDetail(id));
        }
        @GetMapping("/admin/campsite/update")
        public void adminCampsiteUpdate(long id, Model model){
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
       public String campsiteDelete(long id, Model model) {
                model.addAttribute("result", campingService.campsiteDelete(id));
                return "camp/admin/campsite/delete";
        }
        }

