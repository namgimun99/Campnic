package com.lec.spring.service;

import com.lec.spring.domain.QnaComment;
import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.User;
import com.lec.spring.repository.QnaCommentRepository;
import com.lec.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    public void setQnaCommentRepository(QnaCommentRepository qnaCommentRepository) {
        this.qnaCommentRepository = qnaCommentRepository;
    }

    private QnaCommentRepository qnaCommentRepository;
    private UserRepository userRepository;



    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CommentService(){
        System.out.println("CommentService() 생성");
    }

    // 특정 글(id) 의 댓글 목록
    public QryCommentList list(Long id) {
        QryCommentList list = new QryCommentList();

        List<QnaComment> comments = qnaCommentRepository.findByQna(id, Sort.by(Sort.Order.desc("id")));

        list.setCount(comments.size());
        list.setList(comments);
        list.setStatus("OK");

        return list;
    }

    // 특정 글(writeId) 에 특정 사용자(userId) 가 댓글 작성
    public QryResult write(Long qnaId, Long userId, String content) {
        User user = userRepository.findById(userId).orElse(null);   //Object 넘겨받으니까 orElse(null) 써줌

        QnaComment comment = QnaComment.builder()
                .user(user)
                .content(content)
                .qna(qnaId)
                .build();

        qnaCommentRepository.save(comment);

        QryResult result = QryResult.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }

    // 특정 댓글(id) 삭제
    public QryResult delete(Long id) {

        QnaComment comment = qnaCommentRepository.findById(id).orElse(null);
        int count = 0;
        String status = "FAIL";

        if(comment != null){
            qnaCommentRepository.delete(comment);  //DELETE
            count = 1;
            status = "OK";
        }

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}















