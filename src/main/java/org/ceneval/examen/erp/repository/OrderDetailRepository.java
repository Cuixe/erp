package org.ceneval.examen.erp.repository;

import org.ceneval.examen.erp.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrderId(int orderId);

    @Transactional
    @Modifying
    @Query("delete from OrderDetail o where o.orderId=:x")
    void deleteAllByOrderId (@Param("x") int orderId);
}
