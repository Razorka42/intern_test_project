package com.setronica.intern.test.project.controller;

import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.service.ProductClientService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@Validated
public class ProductClientController {
    private final ProductClientService productClientService;

    @Autowired
    public ProductClientController(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<Page<Product>> findProducts(
            @RequestParam @NotBlank @Length(min = 3, max = 3) String currency,
            @RequestParam @NotBlank @Length(min = 3, max = 3) String language,
            Pageable page
    ) {
        return new ResponseEntity<>(productClientService.findAll(currency, language, page), HttpStatus.OK);
    }

    @GetMapping(value = "/products/search")
    public ResponseEntity<Page<Product>> searchProduct(
            @RequestParam @NotBlank @Length(min = 3, max = 3) String currency,
            @RequestParam @NotBlank @Length(min = 3, max = 3) String language,
            @RequestParam @NotBlank String term,
            Pageable page
    ) {
        return new ResponseEntity<>(productClientService.searchProduct(currency, language, term, page), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> findProductByID(
            @Positive @PathVariable(name = "id") long id,
            @RequestParam @NotBlank @Length(min = 3, max = 3) String currency,
            @RequestParam @NotBlank @Length(min = 3, max = 3) String language) {
        return new ResponseEntity<>(productClientService.findById(currency, language, id), HttpStatus.OK);
    }
}
