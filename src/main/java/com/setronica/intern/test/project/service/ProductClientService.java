package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.response.ClientResponsePriceDTO;
import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
import com.setronica.intern.test.project.dto.response.ClientResponseTranslationDTO;
import com.setronica.intern.test.project.exception.ResourceQueryNotFoundException;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import com.setronica.intern.test.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductClientService implements ProductClientServiceInterface {
    private final ProductRepository productRepository;

    @Autowired
    public ProductClientService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ClientResponseProductDTO> findAll(String currency, String language, Pageable page) {
        List<Product> products = productRepository.findAllProducts(currency, language);
        return createClientResponseProductDTOS(currency, language, page, products);
    }

    public Page<ClientResponseProductDTO> searchProduct(String currency, String language, String term, Pageable page) {
        List<Product> products = productRepository.searchProduct(currency, language, term);
        return createClientResponseProductDTOS(currency, language, page, products);
    }

    private Page<ClientResponseProductDTO> createClientResponseProductDTOS(String currency, String language, Pageable page, List<Product> products) {
        List<ClientResponseProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ClientResponseProductDTO productDTO = createProductDTO(product, currency, language);
            productDTOS.add(productDTO);
        }
        return new PageImpl<>(productDTOS, page, 50);
    }

    private ClientResponseProductDTO createProductDTO(Product product, String currency, String language) {
        ClientResponseProductDTO productDTO = new ClientResponseProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        Set<Translation> translations = product.getTranslations();
        for (Translation translation : translations) {
            if (translation.getLanguage().equals(language)) {
                ClientResponseTranslationDTO translationDTO = new ClientResponseTranslationDTO(translation.getName(), translation.getDescription(), translation.getLanguage());
                productDTO.setTranslation(translationDTO);
            }
        }
        Set<Price> prices = product.getPrices();
        for (Price price : prices) {
            if (price.getCurrency().equals(currency)) {
                ClientResponsePriceDTO priceDTO = new ClientResponsePriceDTO(price.getCurrency(), price.getValue());
                productDTO.setPrice(priceDTO);
            }
        }
        return productDTO;
    }

    public ClientResponseProductDTO findById(String currency, String language, Long id) {
        Set<String> errors = new HashSet<>();
        errors.add("currency");
        errors.add("language");

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceQueryNotFoundException("There is no product with such id"));
        checkCurrency(product, errors, currency);
        checkLanguage(product, errors, language);
        if (!errors.isEmpty()) {
            throw new ResourceQueryNotFoundException("The required product has no: " + errors + ".");
        }
        return createProductDTO(product, currency, language);

    }

    private void checkCurrency(Product product, Set<String> errors, String currency) {
        for (Price price : product.getPrices()) {
            if (price.getCurrency().equals(currency)) {
                errors.remove("currency");
                break;
            }
        }
    }

    private void checkLanguage(Product product, Set<String> errors, String language) {
        for (Translation translation : product.getTranslations()) {
            if (translation.getLanguage().equals(language)) {
                errors.remove("language");
                break;
            }
        }
    }


}
