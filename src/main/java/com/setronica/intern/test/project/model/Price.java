package com.setronica.intern.test.project.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;

    @NotNull
    @Positive
    @Digits(integer = 64, fraction = 2)
    private double value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public String getCurrency() {
        return currency;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getValue() {
        return value;
    }

}
