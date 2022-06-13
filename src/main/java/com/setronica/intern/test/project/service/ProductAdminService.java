package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.mapper.AdminMapperInterface;
import com.setronica.intern.test.project.dto.request.AdminRequestPriceDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.request.AdminRequestTranslationDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductAdminService implements ProductAdminServiceInterface {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final TranslationRepository translationRepository;
    private final AdminMapperInterface productMapper;

    @Autowired
    public ProductAdminService(ProductRepository productRepository, PriceRepository priceRepository,
                               TranslationRepository translationRepository, AdminMapperInterface productMapper) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.translationRepository = translationRepository;
        this.productMapper = productMapper;
    }

    public List<AdminResponseProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::mapToResponseProductDTO)
                .collect(Collectors.toList());
    }

    public void create(AdminRequestProductDTO productDTO) {
        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();

        validateUniqueCurrenciesFromProductDTO(priceDTOS);
        validateUniqueLanguagesFromRequestProductDTO(translationDTOS);

        Product product = productMapper.mapToProductFromRequestProductDTO(productDTO);
        productRepository.save(product);
    }

    private void validateUniqueCurrenciesFromProductDTO(Set<AdminRequestPriceDTO> priceDTOS) {
        List<String> currenciesFromDTOList = priceDTOS
                .stream()
                .map(AdminRequestPriceDTO::getCurrency)
                .collect(Collectors.toList());

        String message = "Not unique currency or currencies in product of request";
        validateUniqueProperty(currenciesFromDTOList, message);
    }

    private void validateUniqueLanguagesFromRequestProductDTO(Set<AdminRequestTranslationDTO> translationDTOS) {
        List<String> languagesFromDTOList = translationDTOS
                        .stream()
                        .map(AdminRequestTranslationDTO::getLanguage)
                        .collect(Collectors.toList());
        String message = "Not unique language or languages in product of request";
        validateUniqueProperty(languagesFromDTOList, message);
    }

    private void validateUniqueProperty(List<String> propertyFromDTOList, String message) {
        Set<String> propertyFromDTOSet = new HashSet<>(propertyFromDTOList);
        if (propertyFromDTOSet.size() != propertyFromDTOList.size()) {
            throw new BadRequestException(message);
        }
    }


    public AdminResponseProductDTO findByID(long id) {
        Product product = findProductByID(id);
        return productMapper.mapToResponseProductDTO(product);
    }

    private Product findProductByID(long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void update(long id, AdminRequestProductDTO productDTO) {
        Product current = findProductByID(id);
        Set<AdminRequestPriceDTO> priceDTOS = productDTO.getPrices();
        Set<AdminRequestTranslationDTO> translationDTOS = productDTO.getTranslations();

        validateUniqueCurrenciesFromProductDTO(priceDTOS);
        validateUniqueLanguagesFromRequestProductDTO(translationDTOS);

        Set<Price> currentPrices = current.getPrices();
        Set<Translation> currentTranslations = current.getTranslations();

        Set<Price> updatedPrices = productMapper.mapPricesFromDTO(productDTO, current);
        Set<Translation> updatedTranslations = productMapper.mapTranslationsFromDTO(productDTO, current);
        current.setTranslations(updatedTranslations);
        current.setPrices(updatedPrices);
        productRepository.save(current);
        priceRepository.deleteAll(currentPrices);
        translationRepository.deleteAll(currentTranslations);

    }

    public void delete(long id) {
        productRepository.delete(findProductByID(id));
    }

}
