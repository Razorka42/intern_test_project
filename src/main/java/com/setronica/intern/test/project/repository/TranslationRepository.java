package com.setronica.intern.test.project.repository;

import com.setronica.intern.test.project.model.Translation;
import com.setronica.intern.test.project.model.TranslationsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
}
