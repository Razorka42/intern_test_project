package com.setronica.intern.test.project.dto.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public final class ClientResponseProductDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private ClientResponseTranslationDTO translation;

    @NotNull
    @NotEmpty
    private ClientResponsePriceDTO price;

    @NotEmpty
    @NotNull
    private Date createdAt;
    @NotEmpty
    @NotNull
    private Date updatedAt;

    public ClientResponseProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientResponseTranslationDTO getTranslation() {
        return translation;
    }

    public void setTranslation(ClientResponseTranslationDTO translation) {
        this.translation = translation;
    }

    public ClientResponsePriceDTO getPrice() {
        return price;
    }

    public void setPrice(ClientResponsePriceDTO price) {
        this.price = price;
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
