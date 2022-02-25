package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.model.NameAndDescription;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.repository.NameAndDescriptionRepository;
import com.setronica.intern.test.project.repository.PriceRepository;
import com.setronica.intern.test.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final NameAndDescriptionRepository nameAndDescriptionRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, PriceRepository priceRepository,
                          NameAndDescriptionRepository nameAndDescriptionRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.nameAndDescriptionRepository = nameAndDescriptionRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        product.updateFromProduct(product);
        productRepository.save(product);
    }

    public Product findByID(long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public void update(long id, Product product) {
        Product current = findByID(id);
        Set<Price> currentPrices = current.getPrices();
        Set<NameAndDescription> currentNamesAndDescriptions = current.getNamesAndDescriptions();
        current.updateFromProduct(product);
        priceRepository.deleteAll(currentPrices);
        nameAndDescriptionRepository.deleteAll(currentNamesAndDescriptions);
        productRepository.save(current);
    }

    public void delete(long id) {
        productRepository.delete(findByID(id));
    }

}
