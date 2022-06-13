package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.mapper.ClientMapperInterface;
import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClientService implements ProductClientServiceInterface {
    private final ProductRepository productRepository;
    private final ClientMapperInterface clientMapper;

    @Autowired
    public ProductClientService(ProductRepository productRepository, ClientMapperInterface clientMapper) {
        this.productRepository = productRepository;
        this.clientMapper = clientMapper;
    }

    public Page<ClientResponseProductDTO> findAll(String currency, String language, Pageable page) {
        List<Product> products = productRepository.findAllProducts(currency, language);
        return createClientResponseProductDTOS(currency, language, page, products);
    }

    private Page<ClientResponseProductDTO> createClientResponseProductDTOS(String currency, String language, Pageable page, List<Product> products) {
        List<ClientResponseProductDTO> productDTOS = products.stream()
                .map(product -> clientMapper.mapToResponseProductDTO(product, currency, language))
                .collect(Collectors.toList());
        return new PageImpl<>(productDTOS, page, 50);
    }

    public Page<ClientResponseProductDTO> searchProduct(String currency, String language, String term, Pageable page) {
        List<Product> products = productRepository.searchProduct(currency, language, term);
        return createClientResponseProductDTOS(currency, language, page, products);
    }

    public ClientResponseProductDTO findById(String currency, String language, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceQueryNotFoundException("There is no product with such id"));
        validateCurrency(product, currency);
        validateLanguage(product, language);
        return clientMapper.mapToResponseProductDTO(product, currency, language);
    }

    private void validateCurrency(Product product, String currency) {
        long count = product.getPrices().stream()
                .map(Price::getCurrency)
                .filter(cur -> cur.equals(currency))
                .count();
        if (count == 0) {
            throw new ResourceQueryNotFoundException("The required product has no requested currency");
        }
    }

    private void validateLanguage(Product product, String language) {
        long count = product.getTranslations().stream()
                .map(Translation::getLanguage)
                .filter(lang -> lang.equals(language))
                .count();
        if (count == 0) {
            throw new ResourceQueryNotFoundException("The required product has no requested language");
        }
    }

}
