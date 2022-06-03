package com.setronica.intern.test.project.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TranslationsKey implements Serializable {
    static final long serialVersionUID = 1L;

    public TranslationsKey() {
    }

    public TranslationsKey(Long productId, String language) {
        this.productId = productId;
        this.language = language;
    }

    private Long productId;
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationsKey)) return false;
        TranslationsKey that = (TranslationsKey) o;
        return productId.equals(that.productId) && language.equals(that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, language);
    }
}
