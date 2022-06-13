package com.setronica.intern.test.project.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PriceKey implements Serializable {
    static final long serialVersionUID = 1L;

    public PriceKey() {
    }

    public PriceKey(Product product, String currency) {
        this.product = product;
        this.currency = currency;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String currency;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceKey)) return false;
        PriceKey priceKey = (PriceKey) o;
        return product.equals(priceKey.product) && currency.equals(priceKey.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, currency);
    }

}
