package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductClientServiceInterface {
    Page<ClientResponseProductDTO> findAll(String currency, String language, Pageable page);

    Page<ClientResponseProductDTO> searchProduct(String currency, String language, String term, Pageable page);

    ClientResponseProductDTO findById(String currency, String language, Long id);
}
