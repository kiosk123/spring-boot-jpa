package com.study.springboot.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.study.springboot.domain.Member;

@Repository
public class MemberRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * 저장된 엔티티를 반환할 수도 있지만 side effect 최소화를 위해
     * 저장된 엔티티의 PK값만 반환하여 다시 재조회하는 용도로 만든다
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }
    
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
