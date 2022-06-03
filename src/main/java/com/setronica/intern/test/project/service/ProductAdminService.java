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
        if (isUniqueCurrenciesFromRequestProductDTO(priceDTOS) && isLanguagesFromRequestProductDTO(translationDTOS)) {
            Set<Price> prices = new HashSet<>();
            Product product = new Product();
            priceDTOS.forEach(priceDTO -> {
                Price price = new Price(priceDTO.getCurrency(), product, priceDTO.getValue());
                prices.add(price);
            });

            Set<Translation> translations = new HashSet<>();
            translationDTOS.forEach(translationDTO -> {
                Translation translation = new Translation(translationDTO.getName(), translationDTO.getDescription(), translationDTO.getLanguage(), product);
                translations.add(translation);
            });

            product.setPrices(prices);
            product.setTranslations(translations);
            productRepository.save(product);
        }
    }

    private static boolean isUniqueCurrenciesFromRequestProductDTO(Set<AdminRequestPriceDTO> priceDTOS) {
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

    private static boolean isLanguagesFromRequestProductDTO(Set<AdminRequestTranslationDTO> translationDTOS) {
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

    public static boolean isUnique(List<String> propertyFromDTOList) {
        Set<String> propertyFromDTOSet = new HashSet<>(propertyFromDTOList);
        return propertyFromDTOSet.size() == propertyFromDTOList.size();
    }


    public AdminResponseProductDTO findByID(long id) {
        Product product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return createResponseProductDTO(product);
    }

    private static AdminResponseProductDTO createResponseProductDTO(Product product) {
        Set<AdminResponsePriceDTO> priceDTOS = new HashSet<>();
        Set<AdminResponseTranslationDTO> translationDTOS = new HashSet<>();
        Set<Price> prices = product.getPrices();
        prices.forEach(price -> {
            AdminResponsePriceDTO priceDTO = new AdminResponsePriceDTO(price.getCurrency(), price.getValue());
            priceDTOS.add(priceDTO);
        });
        Set<Translation> translations = product.getTranslations();
        translations.forEach(translation -> {
            AdminResponseTranslationDTO translationDTO = new AdminResponseTranslationDTO(translation.getName(),
                    translation.getDescription(), translation.getLanguage());
            translationDTOS.add(translationDTO);
        });
        return new AdminResponseProductDTO(product.getId(), translationDTOS, priceDTOS, product.getCreatedAt(), product.getUpdatedAt());
    }


    @Transactional
    public void update(long id, AdminRequestProductDTO productDTO) {
        Product current = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();
        if (isUniqueCurrenciesFromRequestProductDTO(priceDTOS) && isLanguagesFromRequestProductDTO(translationDTOS)) {
            Set<Price> currentPrices = current.getPrices();
            Set<Translation> currentTranslations = current.getTranslations();

            Set<Price> updatedPrices = new HashSet<>();
            Set<Translation> updatedTranslations = new HashSet<>();


            priceDTOS.forEach(priceDTO -> {
                Price updatePrice = new Price(priceDTO.getCurrency(), current, priceDTO.getValue());
                updatedPrices.add(updatePrice);
            });


            translationDTOS.forEach(translationDTO -> {
                Translation updateTranslation = new Translation(translationDTO.getName(),
                        translationDTO.getDescription(), translationDTO.getLanguage(), current);
                updatedTranslations.add(updateTranslation);
            });

            current.setTranslations(updatedTranslations);
            current.setPrices(updatedPrices);
            productRepository.save(current);
            priceRepository.deleteAll(currentPrices);
            translationRepository.deleteAll(currentTranslations);
        }
    }

    public void delete(long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

}
