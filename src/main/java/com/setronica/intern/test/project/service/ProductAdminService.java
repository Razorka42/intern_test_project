package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import com.setronica.intern.test.project.repository.PriceRepository;
import com.setronica.intern.test.project.repository.ProductRepository;
import com.setronica.intern.test.project.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProductAdminService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final TranslationRepository translationRepository;

    @Autowired
    public ProductAdminService(ProductRepository productRepository, PriceRepository priceRepository,
                               TranslationRepository translationRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.translationRepository = translationRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void create(Product product) {

        product.getPrices().forEach(x -> x.setProduct(product));
        product.getTranslations().forEach(x -> x.setProduct(product));
        productRepository.save(product);
    }


    public Product findByID(long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void update(long id, Product product) {
        Product current = findByID(id);
        Set<Price> currentPrices = current.getPrices();
        Set<Translation> currentNamesAndDescriptions = current.getTranslations();

        product.getPrices().forEach(x -> x.setProduct(current));
        product.getTranslations().forEach(x -> x.setProduct(current));

        current.setTranslations(product.getTranslations());
        current.setPrices(product.getPrices());
        productRepository.save(current);
        priceRepository.deleteAll(currentPrices);
        translationRepository.deleteAll(currentNamesAndDescriptions);
    }

    public void delete(long id) {
        productRepository.delete(findByID(id));
    }

}
