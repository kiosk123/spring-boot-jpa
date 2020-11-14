package com.study.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.study.springboot.domain.Item;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    
    private final EntityManager em;
    
    public Long save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        }
        else {
            em.merge(item);
        }
        return item.getId();
    }
    
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                 .getResultList();
    }
}
