package com.lec.spring.controller;

import com.lec.spring.domain.City;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.Lender;
import com.lec.spring.domain.LenderValidator;
import com.lec.spring.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/lender")
public class LenderContoller {
    public LenderContoller() {System.out.println(getClass().getName() + "() 생성");}

    private LenderService lenderService;

    @Autowired
    public void setLenderService(LenderService lenderService){
        this.lenderService = lenderService;
    }

    // lender

    @GetMapping("/admin/write")
    public void adminWrite(Model model){
        model.addAttribute("cityList", lenderService.cityList());
    }

    @PostMapping("/admin/write")
    public String adminWriteOk(
            Lender lender
            , @RequestParam("cityId") Long cityId
            , Model model){
        model.addAttribute("lender", lender);
        model.addAttribute("result", lenderService.addLender(lender, cityId));
        return "redirect:/lender/admin/list";
    }

    @GetMapping("/admin/list")
    public void adminList(Model model){
        model.addAttribute("lenderList", lenderService.lenderList());
    }

    @GetMapping("/admin/update")
    public void adminUpdate(Long id, Model model){
        model.addAttribute("cityList", lenderService.cityList());
        model.addAttribute("lender", lenderService.lender(id));
    }

    @PostMapping("/admin/update")
    public String adminUpdateOk(
            @ModelAttribute("lender") Lender lender
            , @RequestParam("cityId") Long cityId
            , Model model){
        model.addAttribute("result", lenderService.lenderUpdate(lender, cityId));
        return "redirect:/lender/admin/list";
    }

    @PostMapping("/admin/delete")
    public String adminDelete(Long id){
        lenderService.deleteLender(id);
        return "redirect:/lender/admin/list";
    }

    // item

    @GetMapping("/admin/itemWrite")
    public void adminItemWrite(
            Long lenderId
            , Model model){
        model.addAttribute("lender", lenderService.lender(lenderId));
    }

    @PostMapping("/admin/itemWrite")
    public String adminItemWriteOk(
            @RequestParam Map<String, MultipartFile> files     // 첨부파일들
            , @ModelAttribute("item") Item item
            , Long lenderId
            , Model model){
        model.addAttribute("result", lenderService.addItem(item, lenderId, files));
        return "lender/admin/itemWriteOk";
    }

    @GetMapping("/admin/itemList")
    public void adminItemList(
            Long lenderId
            , Model model){
        model.addAttribute("lender", lenderService.lender(lenderId));
        model.addAttribute("itemList", lenderService.myItemList(lenderId));
    }

    @GetMapping("/admin/itemUpdate")
    public void adminItemUpdate(
            Long id
            , Model model
    ){
        model.addAttribute("item", lenderService.itemDetail(id));
    }

    @PostMapping("/admin/itemUpdate")
    public String adminItemUpdateOk(
            @ModelAttribute("item") Item item
            , @RequestParam Map<String, MultipartFile> files     // 새로 추가될 첨부파일들
            , Long[] delfile    // 삭제될 파일들
            , Model model
    ){
        model.addAttribute("result", lenderService.updateItem(item, files, delfile));
        return "lender/admin/itemUpdateOk";
    }

    @GetMapping("/admin/itemDetail")
    public void adminItemDetail(
            Long id
            ,Model model){
        model.addAttribute("item", lenderService.itemDetail(id));
    }

    @PostMapping("/admin/itemDelete")
    public String adminItemDelete(
            Long id
            ,@RequestParam("lenderId") Long lenderId
    ){
        lenderService.deleteItem(id);
        return "redirect:/lender/admin/itemList?lenderId=" + lenderId;
    }

    @GetMapping("/itemList")
    public void itemList(){}

    @GetMapping("/itemRent")
    public void itemRent(){}

    @GetMapping("/recipts")
    public void recipts(){}

    @GetMapping("/reciptDelete")
    public String reciptDelete(){
        return "/lender/recipts";
    }
}
