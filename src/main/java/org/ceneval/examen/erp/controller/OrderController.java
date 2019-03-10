package org.ceneval.examen.erp.controller;

import org.ceneval.examen.erp.entity.Order;
import org.ceneval.examen.erp.exception.OrderNotFoundException;
import org.ceneval.examen.erp.repository.OrderDetailRepository;
import org.ceneval.examen.erp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @PostMapping()
    public Order addOrder(@RequestBody Order order) {
        orderRepository.save(order);
        saveDetail(order);
        return order;
    }

    @GetMapping
    public List<Order> getAll() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            order.setOrderDetails(detailRepository.findAllByOrderId(order.getId()));
        });
        return orders;
    }


    @GetMapping("/{id}")
    public Order findById(@PathVariable int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        order.setOrderDetails(detailRepository.findAllByOrderId(order.getId()));
        return order;
    }

    @PutMapping
    public Order update(@RequestBody Order order) {
        orderRepository.save(order);
        detailRepository.deleteAllByOrderId(order.getId());
        saveDetail(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        //order.getOrderDetails().forEach(detailRepository::delete);
        orderRepository.delete(order);
    }

    private void saveDetail(@RequestBody Order order) {
        order.getOrderDetails().forEach(detail -> {
            detail.setOrderId(order.getId());
            detailRepository.save(detail);
        });
    }

}
