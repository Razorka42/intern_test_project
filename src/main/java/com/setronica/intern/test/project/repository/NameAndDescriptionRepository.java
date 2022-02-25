package com.setronica.intern.test.project.repository;

import com.setronica.intern.test.project.model.NameAndDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameAndDescriptionRepository extends JpaRepository<NameAndDescription, Long> {
}
