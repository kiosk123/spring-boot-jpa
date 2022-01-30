package jpabook.jpashop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;

//JUnit5에서는 @RunWith가 @ExtendWith로 변경
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    @Transactional //JUnit 테스트 후 트랜잭션 후 록백
//    @Rollback(false) //이거 붙이면 롤백 안됨
    void testMember() {
        Member member = new Member();
        member.setName("memberA");
        
        memberRepository.save(member);
        Member findMember = memberRepository.findOne(member.getId());
        
        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getName(), findMember.getName());
        assertEquals(member, findMember);
    }

}
