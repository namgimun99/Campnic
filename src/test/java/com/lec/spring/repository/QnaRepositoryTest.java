package com.lec.spring.repository;

import com.lec.spring.domain.Qna;
import com.lec.spring.domain.QnaComment;
import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QnaRepositoryTest {

    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    QnaCommentRepository qnaCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void init(){
        User user1 = userRepository.findByUsername("USER1");
        User user2 = userRepository.findByUsername("USER2");
        User user3 = userRepository.findByUsername("USER3");
        User lender1 = userRepository.findByUsername("LENDER1");
        User camping1 = userRepository.findByUsername("CAMP1");
        User admin1 = userRepository.findByUsername("ADMIN1");

        System.out.println("qna-init() 생성");

        // qna 글 생성
        Qna qna1 = Qna.builder()
                .subject("로그인 안됨")
                .content("컴터 껐다 켜도 로그인이 안되네요..")
                .user(user1)
                .build();

        Qna qna2 = Qna.builder()
                .subject("캠핑장 예약..")
                .content("캠핑장 예약은 어떻게 하는 건가요..?")
                .user(user1)
                .build();

        Qna qna3 = Qna.builder()
                .subject("렌더 권한 수락!")
                .content("며칠 기다려도 안되네요 권한 수락 빨리 해주세요!!")
                .user(user2)
                .build();

        Qna qna4 = Qna.builder()
                .subject("캠핑 권한 좀..")
                .content("캠핑 권한 부탁드립니다 2일이 지났네요..")
                .user(user3)
                .build();

        Qna qna5 = Qna.builder()
                .subject("아이템 등록")
                .content("아무 물품이나 등록 가능한가요? 궁금합니다.")
                .user(lender1)
                .build();

        Qna qna6 = Qna.builder()
                .subject("캠핑장 등록")
                .content("애견전용 캠핑장 등록도 하고 싶은데 나중에 따로 카테고리도 부탁드립니다.")
                .user(camping1)
                .build();

        Qna qna7 = Qna.builder()
                .subject("※공지※")
                .content("저희 사이트를 이용해주고 계신 모든 분들께 감사드립니다. 곧 애견 전용 캠핑장 카테고리가 등록될 예정입니다. 많이 기대해주시면 감사하겠습니다.")
                .user(admin1)
                .build();

        qna1 = qnaRepository.save(qna1);
        qna2 = qnaRepository.save(qna2);
        qna3 = qnaRepository.save(qna3);
        qna4 = qnaRepository.save(qna4);
        qna5 = qnaRepository.save(qna5);
        qna6 = qnaRepository.save(qna6);
        qna7 = qnaRepository.save(qna7);

        qnaRepository.findAll().forEach(System.out::println);

        // qna comment 생성
        System.out.println("------qna-comment 생성------");

        QnaComment c1 = QnaComment.builder()
                .content("어 저도 그러네요. 지금은 되시나요?")
                .user(user2)
                .qna(qna1.getId())
                .build();

        QnaComment c2 = QnaComment.builder()
                .content("빠른 시일 내로 도움 드리겠습니다. 감사합니다.")
                .user(admin1)
                .qna(qna1.getId())
                .build();

        QnaComment c3 = QnaComment.builder()
                .content("메인 페이지에 가시면 캠핑장 카테고리 있어요 그거 클릭하시면 됩니다")
                .user(user2)
                .qna(qna2.getId())
                .build();

        QnaComment c4 = QnaComment.builder()
                .content("관리자가 확인했으니 오늘 내로 빠른 조치해드리겠습니다.")
                .user(admin1)
                .qna(qna3.getId())
                .build();

        QnaComment c5 = QnaComment.builder()
                .content("헐 저도 그래요")
                .user(user2)
                .qna(qna4.getId())
                .build();

        QnaComment c6 = QnaComment.builder()
                .content("관리자가 확인했으니 오늘 내로 빠른 조치해드리겠습니다.")
                .user(admin1)
                .qna(qna4.getId())
                .build();

        QnaComment c7 = QnaComment.builder()
                .content("캠핑용품이 주요 품목 아닌가요?")
                .user(user1)
                .qna(qna5.getId())
                .build();

        QnaComment c8 = QnaComment.builder()
                .content("맞아여 렌더들은 거의 캠핑용품만 팔던데요?")
                .user(user3)
                .qna(qna5.getId())
                .build();

        QnaComment c9 = QnaComment.builder()
                .content("맞습니다. 저도 주로 캠핑용품을 빌려드리고 있습니다")
                .user(lender1)
                .qna(qna5.getId())
                .build();

        QnaComment c10 = QnaComment.builder()
                .content("저희 서비스에 관심을 가져주셔서 감사합니다. 품목들은 캠핑용품이 주를 이루고 있으나 옮기기 어려운 무겁거나 큰 품목들은 제한하고 있습니다. 감사합니다.")
                .user(admin1)
                .qna(qna6.getId())
                .build();

        QnaComment c11 = QnaComment.builder()
                .content("저도 강아지를 키우고 있는데 카테고리가 있으면 너무 좋겠네요!")
                .user(user1)
                .qna(qna7.getId())
                .build();

        QnaComment c12 = QnaComment.builder()
                .content("고양이도...")
                .user(user3)
                .qna(qna7.getId())
                .build();

        QnaComment c13 = QnaComment.builder()
                .content("목록에 있는 공지글 확인해주시면 감사하겠습니다^^")
                .user(admin1)
                .qna(qna7.getId())
                .build();

        c1 = qnaCommentRepository.save(c1);
        c2 = qnaCommentRepository.save(c2);
        c3 = qnaCommentRepository.save(c3);
        c4 = qnaCommentRepository.save(c4);
        c5 = qnaCommentRepository.save(c5);
        c6 = qnaCommentRepository.save(c6);
        c7 = qnaCommentRepository.save(c7);
        c8 = qnaCommentRepository.save(c8);
        c9 = qnaCommentRepository.save(c9);
        c10 = qnaCommentRepository.save(c10);
        c11 = qnaCommentRepository.save(c11);
        c12 = qnaCommentRepository.save(c12);
        c13 = qnaCommentRepository.save(c13);

        qnaCommentRepository.findAll().forEach(System.out::println);
    }
}