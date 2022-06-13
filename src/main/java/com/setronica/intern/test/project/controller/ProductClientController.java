package com.setronica.intern.test.project.controller;

import com.setronica.intern.test.project.dto.response.ClientResponseProductDTO;
import com.setronica.intern.test.project.service.ProductClientServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping(produces = {"application/json"})
public class ProductClientController {
    private final ProductClientServiceInterface productClientService;

    @Autowired
    public ProductClientController(ProductClientServiceInterface productClientService) {
        this.productClientService = productClientService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<Page<ClientResponseProductDTO>> findProducts(
            @RequestParam @NotBlank String currency,
            @RequestParam @NotBlank String language,
            Pageable page
    ) {
        return new ResponseEntity<>(productClientService.findAll(currency, language, page), HttpStatus.OK);
    }

    @GetMapping(value = "/products/search")
    public ResponseEntity<Page<ClientResponseProductDTO>> searchProduct(
            @RequestParam @NotBlank String currency,
            @RequestParam @NotBlank String language,
            @RequestParam @NotBlank String term,
            Pageable page
    ) {
        return new ResponseEntity<>(productClientService.searchProduct(currency, language, term, page), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ClientResponseProductDTO> findProductByID(
            @Positive @PathVariable(name = "id") long id,
            @RequestParam @NotBlank String currency,
            @RequestParam @NotBlank String language) {
        return new ResponseEntity<>(productClientService.findById(currency, language, id), HttpStatus.OK);
    }
}
