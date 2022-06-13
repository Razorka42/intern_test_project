package com.setronica.intern.test.project.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TranslationsKey implements Serializable {
    static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String language;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TranslationsKey() {
    }

    public TranslationsKey(Product product, String language) {
        this.product = product;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationsKey)) return false;
        TranslationsKey that = (TranslationsKey) o;
        return product.equals(that.product) && language.equals(that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, language);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
