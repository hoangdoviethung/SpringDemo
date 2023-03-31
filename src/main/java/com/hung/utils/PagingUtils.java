package com.hung.utils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author itsol.hungtt
 */
public class PagingUtils {

    public static <T> Long countTotalElement(EntityManager em, CriteriaBuilder cb, List<Predicate> predicates, Class<T> clazz) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(clazz)));
        countQuery.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(countQuery).getSingleResult();
    }

    public static <T> Long countTotalElement(EntityManager em, CriteriaBuilder cb, Predicate[] predicates, Class<T> clazz) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(clazz)));
        countQuery.where(predicates);
        return em.createQuery(countQuery).getSingleResult();
    }
}
