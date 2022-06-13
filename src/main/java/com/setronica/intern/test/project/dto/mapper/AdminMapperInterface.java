package com.setronica.intern.test.project.dto.mapper;

import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;

import java.util.Set;

public interface AdminMapperInterface {
    Product mapToProductFromRequestProductDTO(AdminRequestProductDTO productDTO);

    AdminResponseProductDTO mapToResponseProductDTO(Product product);

    Set<Price> mapPricesFromDTO(AdminRequestProductDTO productDTO, Product product);

    Set<Translation> mapTranslationsFromDTO(AdminRequestProductDTO productDTO, Product product);

}
