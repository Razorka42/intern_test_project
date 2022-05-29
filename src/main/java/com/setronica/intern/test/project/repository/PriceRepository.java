package com.setronica.intern.test.project.repository;

import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.PriceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
