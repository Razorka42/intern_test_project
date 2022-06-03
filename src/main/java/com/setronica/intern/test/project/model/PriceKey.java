package com.setronica.intern.test.project.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PriceKey implements Serializable {
    static final long serialVersionUID = 1L;

    public PriceKey() {
    }

    public PriceKey(Long productId, String currency) {
        this.productId = productId;
        this.currency = currency;
    }

    @Column(name = "product_id")
    private Long productId;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceKey)) return false;
        PriceKey priceKey = (PriceKey) o;
        return productId.equals(priceKey.productId) && currency.equals(priceKey.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, currency);
    }
}
