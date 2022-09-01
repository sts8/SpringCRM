package com.sts8.springcrm.repository;

import com.sts8.springcrm.model.OrderContainsArticleRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderContainsArticleRepository extends CrudRepository<OrderContainsArticleRelation, Integer> {

    List<OrderContainsArticleRelation> findAllByOrderId(int orderId);

    boolean existsByArticleId(int articleId);

}
