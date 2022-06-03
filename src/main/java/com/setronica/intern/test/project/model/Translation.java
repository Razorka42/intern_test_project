package com.setronica.intern.test.project.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "translations")
public class Translation implements Serializable {

    //    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    private long id;
    @EmbeddedId
    private TranslationsKey translationsKey;
    @NotNull
    @NotBlank
    @Length(max = 255)
    private String name;

    @Length(max = 255)
    private String description;


    @NotNull
    @NotBlank
    @Column(insertable = false, updatable = false)
    private String language;

//    @Id
//    @NotNull
//    @NotBlank
//    @Column(name = "product_id", insertable = false, updatable = false)
//    private Long productId;


    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    private Product product;

    public Translation(String name, String description, String language, Product product) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.product = product;
        translationsKey = new TranslationsKey(this.getProduct().getId(), this.getLanguage());
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

    public void setTranslationsKey(TranslationsKey translationsKey) {
        this.translationsKey = translationsKey;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
