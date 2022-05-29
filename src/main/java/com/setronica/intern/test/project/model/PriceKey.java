package com.setronica.intern.test.project.model;

import java.io.Serializable;
import java.util.Objects;


public class PriceKey implements Serializable {
    static final long serialVersionUID = 1L;

    public PriceKey() {
    }


    private Product product;

    private String currency;

    public Product getProduct() {
        return product;
    }

    public String getCurrency() {
        return currency;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceKey)) return false;
        PriceKey priceKey = (PriceKey) o;
        return getProduct().equals(priceKey.getProduct()) && getCurrency().equals(priceKey.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getCurrency());
    }
}
