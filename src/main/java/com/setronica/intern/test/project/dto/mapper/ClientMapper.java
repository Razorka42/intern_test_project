package com.setronica.intern.test.project.dto.mapper;

import com.setronica.intern.test.project.dto.response.ClientResponsePriceDTO;
import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
import com.setronica.intern.test.project.dto.response.ClientResponseTranslationDTO;
import com.setronica.intern.test.project.exception.MapperException;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public final class ClientMapper implements ClientMapperInterface {
    private final ModelMapper modelMapper;
    private final String MESSAGE = "Business logic error while mapping";

    @Autowired
    public ClientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void setupMapper() {
        modelMapper.createTypeMap(Product.class, ClientResponseProductDTO.class)
                .addMappings(m -> m.skip(ClientResponseProductDTO::setPrice))
                .addMappings(m -> m.skip(ClientResponseProductDTO::setTranslation))
                .setPostConverter(toResponseDTOFromProductConverter());
    }

    public ClientResponseProductDTO mapToResponseProductDTO(Product product, String currency, String language) {
        processFilterPrices(product, currency);
        processFilterLanguage(product, language);
        return modelMapper.map(product, ClientResponseProductDTO.class);
    }

    private Converter<Product, ClientResponseProductDTO> toResponseDTOFromProductConverter() {
        return context -> {
            Product source = context.getSource();
            ClientResponseProductDTO destination = context.getDestination();
            setPriceForResponseDTO(source, destination);
            setTranslationForResponseDTO(source, destination);
            return context.getDestination();
        };
    }

    private void processFilterPrices(Product product, String currency) {
        product.setPrices(
                product.getPrices().stream()
                        .filter(price -> price.getCurrency().equals(currency))
                        .collect(Collectors.toSet())
        );
    }

    private void processFilterLanguage(Product product, String language) {
        product.setTranslations(
                product.getTranslations().stream()
                        .filter(translation -> translation.getLanguage().equals(language))
                        .collect(Collectors.toSet())
        );
    }

    private void setPriceForResponseDTO(Product product, ClientResponseProductDTO productDTO) {
        Price price = product.getPrices()
                .stream()
                .findFirst()
                .orElseThrow(() -> new MapperException(MESSAGE));
        ClientResponsePriceDTO priceDTO = modelMapper.map(price, ClientResponsePriceDTO.class);
        productDTO.setPrice(priceDTO);
    }

    private void setTranslationForResponseDTO(Product product, ClientResponseProductDTO productDTO) {
        Translation translation = product.getTranslations()
                .stream()
                .findFirst().orElseThrow(() -> new MapperException(MESSAGE));
        ClientResponseTranslationDTO translationDTO = modelMapper.map(translation, ClientResponseTranslationDTO.class);
        productDTO.setTranslation(translationDTO);
    }
}
