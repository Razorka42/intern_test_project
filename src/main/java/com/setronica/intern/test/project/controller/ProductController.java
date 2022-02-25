package com.setronica.intern.test.project.controller;

import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> create(@RequestBody @Valid Product product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> read() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> read(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(productService.findByID(id), HttpStatus.OK);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody @Valid Product product) {
        productService.update(id, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
