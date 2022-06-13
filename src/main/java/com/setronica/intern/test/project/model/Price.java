package com.setronica.intern.test.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "price")
public class Price implements Serializable {
    @EmbeddedId
    private PriceKey priceKey;

    @Column(insertable = false, updatable = false)
    private String currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    private BigDecimal value;

    public Price(String currency, BigDecimal value, Product product) {
        this.currency = currency;
        this.product = product;
        this.value = value;
        priceKey = new PriceKey(getProduct(), getCurrency());
    }

    public Price() {
    }

    public PriceKey getPriceKey() {
        return priceKey;
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
