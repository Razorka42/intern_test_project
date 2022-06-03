package com.setronica.intern.test.project.dto.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


public final class AdminRequestProductDTO {

    @NotNull
    @NotEmpty
    private Set<AdminRequestTranslationDTO> translations;

    @NotNull
    @NotEmpty
    private Set<AdminRequestPriceDTO> prices;
    private Date createdAt;
    private Date updatedAt;

    public AdminRequestProductDTO() {
    }

    public AdminRequestProductDTO(Set<AdminRequestTranslationDTO> translations, Set<AdminRequestPriceDTO> prices) {
        this.translations = translations;
        this.prices = prices;
    }

    public Set<AdminRequestTranslationDTO> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<AdminRequestTranslationDTO> translations) {
        this.translations = translations;
    }

    public Set<AdminRequestPriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(Set<AdminRequestPriceDTO> prices) {
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
