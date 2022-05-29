package com.setronica.intern.test.project.model;

import java.io.Serializable;

public class TranslationsKey implements Serializable {
    static final long serialVersionUID = 1L;

    public TranslationsKey() {
    }
    private Product product;
    private String language;

    public Product getProduct() {
        return product;
    }

    public String getLanguage() {
        return language;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
