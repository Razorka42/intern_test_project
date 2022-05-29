package com.setronica.intern.test.project.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@IdClass(PriceKey.class)
@Table(name = "price")
public class Price {

    @Id
    @NotNull
    @NotBlank
    private String currency;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @Positive
    @Digits(integer = 64, fraction = 2)
    private BigDecimal value;

    public String getCurrency() {
        return currency;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getValue() {
        return value;
    }

}
