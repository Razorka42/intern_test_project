package com.setronica.intern.test.project.dto.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

public class AdminResponseProductDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private Set<AdminResponseTranslationDTO> translations;

    @NotNull
    @NotEmpty
    private Set<AdminResponsePriceDTO> prices;
    @NotNull
    @NotEmpty
    private Date createdAt;
    @NotNull
    @NotEmpty
    private Date updatedAt;

    public AdminResponseProductDTO() {
    }

    public AdminResponseProductDTO(Long id, Set<AdminResponseTranslationDTO> translations, Set<AdminResponsePriceDTO> prices, Date createdAt, Date updatedAt) {
        this.id = id;
        this.translations = translations;
        this.prices = prices;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<AdminResponseTranslationDTO> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<AdminResponseTranslationDTO> translations) {
        this.translations = translations;
    }

    public Set<AdminResponsePriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(Set<AdminResponsePriceDTO> prices) {
        this.prices = prices;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
