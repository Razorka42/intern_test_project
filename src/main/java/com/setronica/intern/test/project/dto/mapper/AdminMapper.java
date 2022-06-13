package com.setronica.intern.test.project.dto.mapper;


import com.setronica.intern.test.project.dto.request.AdminRequestPriceDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestTranslationDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class AdminMapper implements AdminMapperInterface {

    private final ModelMapper modelMapper;

    @Autowired
    public AdminMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void setupMapper() {
        modelMapper.createTypeMap(AdminRequestProductDTO.class, Product.class)
                .addMappings(m -> m.skip(Product::setPrices))
                .addMappings(m -> m.skip(Product::setTranslations))
                .setPostConverter(toProductFromRequestConverter());
    }

    public Product mapToProductFromRequestProductDTO(AdminRequestProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public AdminResponseProductDTO mapToResponseProductDTO(Product product) {
        return modelMapper.map(product, AdminResponseProductDTO.class);
    }

    private Converter<AdminRequestProductDTO, Product> toProductFromRequestConverter() {
        return context -> {
            AdminRequestProductDTO source = context.getSource();
            Product destination = context.getDestination();
            setPricesForProduct(source, destination);
            setTranslationsForProduct(source, destination);
            return context.getDestination();
        };
    }

    private void setPricesForProduct(AdminRequestProductDTO productDTO, Product product) {
        Set<Price> prices = mapPricesFromDTO(productDTO, product);
        product.setPrices(prices);
    }

    public Set<Price> mapPricesFromDTO(AdminRequestProductDTO productDTO, Product product) {

        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        return priceDTOS.stream()
                .map(priceDTO -> new Price(priceDTO.getCurrency(), priceDTO.getValue(), product))
                .collect(Collectors.toSet());
    }

    private void setTranslationsForProduct(AdminRequestProductDTO productDTO, Product product) {
        Set<Translation> translations = mapTranslationsFromDTO(productDTO, product);
        product.setTranslations(translations);
    }

    public Set<Translation> mapTranslationsFromDTO(AdminRequestProductDTO productDTO, Product product) {
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();

        return translationDTOS.stream()
                .map(translationDTO -> new Translation(
                                translationDTO.getName(),
                                translationDTO.getDescription(),
                                translationDTO.getLanguage(),
                                product
                        )
                )
                .collect(Collectors.toSet());
    }


}
