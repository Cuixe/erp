package org.ceneval.examen.erp.repository;

import org.ceneval.examen.erp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Client, Integer> {
}
