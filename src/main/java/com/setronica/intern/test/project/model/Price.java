package com.setronica.intern.test.project.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "price")
public class Price implements Serializable {
    @EmbeddedId
    private PriceKey priceKey;

    @NotNull
    @NotBlank
    @Column(insertable = false, updatable = false)
    private String currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    private Product product;

    @NotNull
    @Positive
    @Digits(integer = 64, fraction = 2)
    private BigDecimal value;

    public Price(String currency, Product product, BigDecimal value) {
        this.currency = currency;
        this.product = product;
        this.value = value;
        priceKey = new PriceKey(getProduct().getId(), getCurrency());
    }

    public Price() {
    }

    public PriceKey getPriceKey() {
        return priceKey;
    }

    public void setPriceKey(PriceKey priceKey) {
        this.priceKey = priceKey;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Product getProduct() {
        return product;
    }

    public Price(String currency) {
        this.currency = currency;
    }

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
