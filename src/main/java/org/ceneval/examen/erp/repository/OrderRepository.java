package org.ceneval.examen.erp.repository;

import org.ceneval.examen.erp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
