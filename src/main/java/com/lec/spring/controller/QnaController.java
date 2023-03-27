package com.lec.spring.controller;

import com.lec.spring.domain.Qna;
import com.lec.spring.service.QnaService;
import com.lec.spring.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

// Controller layer
//  request 처리 ~ response
@Controller
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    public void setQnaService(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    private QnaService qnaService;

    public QnaController(){
        System.out.println("QnaController() 생성");
    }

    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeOk(
             @ModelAttribute("dto") Qna qna
            , Model model
    ){
        model.addAttribute("result", qnaService.write(qna));
        return "qna/writeOk";
    }

    @GetMapping("/detail")
    public void detail(long id, Model model){
        model.addAttribute("list", qnaService.detail(id));
        model.addAttribute("conPath", U.getRequest().getContextPath());
    }

    // 페이징 사용
    @GetMapping("/list")
    public void list(Integer page, Model model){
//        model.addAttribute("list", boardService.list());
        qnaService.list(page, model);
    }

    @GetMapping("/update")
    public void update(long id, Model model){
        model.addAttribute("list", qnaService.selectById(id));
    }

    @PostMapping("/update")
    public String updateOk(
            @ModelAttribute("dto") Qna qna
            , Model model){
        model.addAttribute("result", qnaService.update(qna));
        return "qna/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(long id, Model model){
        model.addAttribute("result", qnaService.deleteById(id));
        return "qna/deleteOk";
    }

    // 이 컨트롤러 클래스의 handler 에서 폼 데이터를 바인딩 할때 검증하는 Validator 객체 지정
//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        System.out.println("initBinder() 호출");
//        binder.setValidator(new WriteValidator());
//    }

    // 페이징
    // pageRows 변경시 동작
    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/board/list?page=" + page;
    }

}










