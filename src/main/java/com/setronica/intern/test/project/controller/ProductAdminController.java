package com.setronica.intern.test.project.controller;

import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
import com.setronica.intern.test.project.service.ProductAdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/admin")
public class ProductAdminController {

    private final ProductAdminServiceInterface productService;

    @Autowired
    public ProductAdminController(ProductAdminServiceInterface productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> create(@RequestBody @Valid AdminRequestProductDTO productDTO) {
        productService.create(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<AdminResponseProductDTO>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<AdminResponseProductDTO> findById(@Positive @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(productService.findByID(id), HttpStatus.OK);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> update(@Positive @PathVariable(name = "id") long id, @RequestBody @Valid AdminRequestProductDTO productDTO) {
        productService.update(id, productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> delete(@Positive @PathVariable(name = "id") long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
