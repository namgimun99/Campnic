package com.lec.spring.service;

import com.lec.spring.common.C;
import com.lec.spring.domain.Qna;
import com.lec.spring.domain.User;
import com.lec.spring.repository.QnaRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class QnaService {

    private QnaRepository qnaRepository;
    private UserRepository userRepository;

    @Autowired
    public void setQnaRepository(QnaRepository qnaRepository){this.qnaRepository = qnaRepository; }
    @Autowired
    public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository; }

    public QnaService(){
        System.out.println("QnaService() 생성");
    }

    public List<Qna> listAdmin() {
        List<Qna> notice = qnaRepository.findAllByUserId(8L);
        return notice;
    }

    public List<Qna> myqnaList(Long userId){
        return qnaRepository.findAllByUserId(userId);
    }

    // 페이징 리스트
    public List<Qna> list(Integer page, Model model){

        if(page == null) page = 1;  // 디폴트 1페이지
        if(page < 1) page = 1;

        HttpSession session= U.getSession();
        Integer writePages = (Integer) session.getAttribute("writePages");
        if(writePages == null) writePages = C.WRITE_PAGES;
        Integer pageRows = (Integer) session.getAttribute("pageRows");
        if(pageRows == null) pageRows = C.PAGE_ROWS;
        session.setAttribute("page", page);

        Page<Qna> pageWrites = qnaRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();   // 글 목록 전체 개수
        int totalPage = pageWrites.getTotalPages(); // 총 페이지

        // page값 보정
        if(page > totalPage) page = totalPage;

        int startPage = ((int)((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if(endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        // 해당 페이지의 글 목록 읽어오기
        List<Qna> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

    // 글 작성
    public int write(Qna qna){

        User user = U.getLoggedUser();

        user = userRepository.findById(user.getId()).orElse(null);
        qna.setUser(user);

        qna = qnaRepository.saveAndFlush(qna);

        return 1;
    }

    // 글 상세보기
    @Transactional
    public List<Qna> detail(long id){
        List<Qna> list = new ArrayList<>();

        Qna qna = qnaRepository.findById(id).orElse(null);
        if(qna != null){
            list.add(qna);
        }

        return list;
    }

    // update 위해 만든 상세보기
    public List<Qna> selectById(long id){
        List<Qna> list = new ArrayList<>();

        Qna qna = qnaRepository.findById(id).orElse(null);

        if(qna != null){
            list.add(qna);
        }

        return  list;
    }

    // 글 수정
    public int update(Qna qna){
        int result = 0;

        Qna q = qnaRepository.findById(qna.getId()).orElse(null);
        if(q != null){
            q.setSubject((qna.getSubject()));
            q.setContent((qna.getContent()));
            qnaRepository.save(q);  // UPDATE
            result = 1;
        }
    return result;
    }

    // 해당 글 삭제
    public int deleteById(long id){
        int result = 0;

        Qna qna = qnaRepository.findById(id).orElse(null);

        if(qna != null){
            qnaRepository.delete(qna);
            result = 1;
        }
        return result;
    }

}
