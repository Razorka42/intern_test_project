package com.setronica.intern.test.project.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "translations")
public class Translation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Length(max = 255)
    private String name;

    @Length(max = 255)
    private String description;

    @NotNull
    @NotBlank
    @Length(min = 3, max = 3)
    private String language;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

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
}
