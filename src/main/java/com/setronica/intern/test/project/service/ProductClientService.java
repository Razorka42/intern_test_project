package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.exception.ResourceQueryNotFoundException;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import com.setronica.intern.test.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductClientService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductClientService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(String currency, String language, Pageable page) {

        return productRepository.findAllProducts(currency, language, page);
    }

    public Page<Product> searchProduct(String currency, String language, String term, Pageable page) {
        return productRepository.searchProduct(currency, language, term, page);
    }

    public Product findById(String currency, String language, Long id) {
        Set<String> errors = new HashSet<>();
        errors.add("currency");
        errors.add("language");

        Product product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        for (Price price : product.getPrices()) {
            if (price.getCurrency().equals(currency)) {
                errors.remove("currency");
                break;
            }
        }
        for (Translation translation : product.getTranslations()) {
            if (translation.getLanguage().equals(language)) {
                errors.remove("language");
                break;
            }
        }
        if (!errors.isEmpty()) {
            throw new ResourceQueryNotFoundException("The required product has no: " + errors + ".");
        }
        return product;

    }

}
