package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.request.AdminRequestPriceDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestTranslationDTO;
import com.setronica.intern.test.project.dto.response.AdminResponsePriceDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseTranslationDTO;
import com.setronica.intern.test.project.exception.BadRequestException;
import com.setronica.intern.test.project.model.Price;
import com.setronica.intern.test.project.model.Product;
import com.setronica.intern.test.project.model.Translation;
import com.setronica.intern.test.project.repository.PriceRepository;
import com.setronica.intern.test.project.repository.ProductRepository;
import com.setronica.intern.test.project.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductAdminService implements ProductAdminServiceInterface {
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

    public List<AdminResponseProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<AdminResponseProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            AdminResponseProductDTO productDTO = createResponseProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public void create(AdminRequestProductDTO productDTO) {
        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();
        if (isUniqueCurrenciesFromRequestProductDTO(priceDTOS) && isUniqueLanguagesFromRequestProductDTO(translationDTOS)) {
            Product product = new Product();
            Set<Price> prices = createPricesFromDTOPrices(priceDTOS, product);
            Set<Translation> translations = createTranslationsFromDTOTranslations(translationDTOS, product);
            product.setPrices(prices);
            product.setTranslations(translations);
            productRepository.save(product);
        }
    }

    private boolean isUniqueCurrenciesFromRequestProductDTO(Set<AdminRequestPriceDTO> priceDTOS) {
        List<String> currenciesFromDTOList = new ArrayList<>();
        priceDTOS.forEach(priceDTO -> {
            String currencyFromPriceDTO = priceDTO.getCurrency();
            currenciesFromDTOList.add(currencyFromPriceDTO);
        });

        if (!isUnique(currenciesFromDTOList)) {
            throw new BadRequestException("Not unique currency or currencies in product of request");
        }
        return true;
    }

    private boolean isUniqueLanguagesFromRequestProductDTO(Set<AdminRequestTranslationDTO> translationDTOS) {
        List<String> languagesFromDTOList = new ArrayList<>();
        translationDTOS.forEach(translationDTO -> {
            String languageFromTranslationDTO = translationDTO.getLanguage();
            languagesFromDTOList.add(languageFromTranslationDTO);
        });

        if (!isUnique(languagesFromDTOList)) {
            throw new BadRequestException("Not unique language or languages in product of request");
        }
        return true;
    }

    private boolean isUnique(List<String> propertyFromDTOList) {
        Set<String> propertyFromDTOSet = new HashSet<>(propertyFromDTOList);
        return propertyFromDTOSet.size() == propertyFromDTOList.size();
    }

    private Set<Price> createPricesFromDTOPrices(Set<AdminRequestPriceDTO> priceDTOS, Product product) {
        Set<Price> prices = new HashSet<>();
        priceDTOS.forEach(priceDTO -> {
            Price price = new Price(priceDTO.getCurrency(), product, priceDTO.getValue());
            prices.add(price);
        });
        return prices;
    }

    private Set<Translation> createTranslationsFromDTOTranslations(Set<AdminRequestTranslationDTO> translationDTOS, Product product) {
        Set<Translation> translations = new HashSet<>();
        translationDTOS.forEach(translationDTO -> {
            Translation translation = new Translation(translationDTO.getName(), translationDTO.getDescription(), translationDTO.getLanguage(), product);
            translations.add(translation);
        });
        return translations;
    }

    public AdminResponseProductDTO findByID(long id) {
        Product product = findProductByID(id);
        return createResponseProductDTO(product);
    }

    private Product findProductByID(long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    private AdminResponseProductDTO createResponseProductDTO(Product product) {
        Set<Price> prices = product.getPrices();
        Set<AdminResponsePriceDTO> priceDTOS = createResponsePriceDTOs(prices);
        Set<Translation> translations = product.getTranslations();
        Set<AdminResponseTranslationDTO> translationDTOS = createResponseTranslationsDTOs(translations);
        return new AdminResponseProductDTO(product.getId(), translationDTOS, priceDTOS, product.getCreatedAt(), product.getUpdatedAt());
    }

    private Set<AdminResponsePriceDTO> createResponsePriceDTOs(Set<Price> prices) {
        Set<AdminResponsePriceDTO> priceDTOS = new HashSet<>();
        prices.forEach(price -> {
            AdminResponsePriceDTO priceDTO = new AdminResponsePriceDTO(price.getCurrency(), price.getValue());
            priceDTOS.add(priceDTO);
        });
        return priceDTOS;
    }

    private Set<AdminResponseTranslationDTO> createResponseTranslationsDTOs(Set<Translation> translations) {
        Set<AdminResponseTranslationDTO> translationDTOS = new HashSet<>();
        translations.forEach(translation -> {
            AdminResponseTranslationDTO translationDTO = new AdminResponseTranslationDTO(translation.getName(),
                    translation.getDescription(), translation.getLanguage());
            translationDTOS.add(translationDTO);
        });
        return translationDTOS;
    }

    @Transactional
    public void update(long id, AdminRequestProductDTO productDTO) {
        Product current = findProductByID(id);
        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();
        if (isUniqueCurrenciesFromRequestProductDTO(priceDTOS) && isUniqueLanguagesFromRequestProductDTO(translationDTOS)) {
            Set<Price> currentPrices = current.getPrices();
            Set<Translation> currentTranslations = current.getTranslations();

            Set<Price> updatedPrices = createPricesFromDTOPrices(priceDTOS, current);
            Set<Translation> updatedTranslations = createTranslationsFromDTOTranslations(translationDTOS, current);
            current.setTranslations(updatedTranslations);
            current.setPrices(updatedPrices);
            productRepository.save(current);
            priceRepository.deleteAll(currentPrices);
            translationRepository.deleteAll(currentTranslations);
        }
    }

    public void delete(long id) {
        productRepository.delete(findProductByID(id));
    }

}
