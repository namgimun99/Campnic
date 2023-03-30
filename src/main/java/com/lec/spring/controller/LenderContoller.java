package com.lec.spring.controller;

import com.lec.spring.domain.City;
import com.lec.spring.domain.Lender;
import com.lec.spring.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lender")
public class LenderContoller {
    public LenderContoller() {System.out.println(getClass().getName() + "() 생성");}

    private LenderService lenderService;

    @Autowired
    public void setLenderService(LenderService lenderService){
        this.lenderService = lenderService;
    }

    @GetMapping("/admin/write")
    public void adminWrite(Model model){
        model.addAttribute("cityList", lenderService.cityList());
    }

    @PostMapping("/admin/write")
    public String adminWriteOk(@ModelAttribute("lender") Lender lender
            , @RequestParam("cityId") Long cityId
            , Model model){
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

    @GetMapping("/admin/itemWrite")
    public void adminItemWrite(){}

    @GetMapping("/admin/itemList")
    public void adminItemList(){}

    @GetMapping("/admin/itemUpdate")
    public void adminItemUpdate(){}

    @GetMapping("/admin/itemDetail")
    public void adminItemDetail(){}

    @GetMapping("/admin/itemDelete")
    public String adminItemDelete(){

        return "lender/admin/itemList";
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
