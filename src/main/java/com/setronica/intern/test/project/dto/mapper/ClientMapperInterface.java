package com.setronica.intern.test.project.dto.mapper;

import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
import com.setronica.intern.test.project.model.Product;

public interface ClientMapperInterface {
    ClientResponseProductDTO mapToResponseProductDTO(Product product, String currency, String language);
}
