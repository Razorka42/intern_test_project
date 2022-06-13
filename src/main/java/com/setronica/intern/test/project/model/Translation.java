package com.setronica.intern.test.project.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "translations")
public class Translation implements Serializable {
    @EmbeddedId
    private TranslationsKey translationsKey;

    @Length(max = 255)
    private String name;

    @Length(max = 255)
    private String description;

    @Column(insertable = false, updatable = false)
    private String language;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    public Translation(String name, String description, String language, Product product) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.product = product;
        translationsKey = new TranslationsKey(getProduct(), getLanguage());
    }

    public Translation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TranslationsKey getTranslationsKey() {
        return translationsKey;
    }

    public Product getProduct() {
        return product;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
