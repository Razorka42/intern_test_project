package com.setronica.intern.test.project.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NameAndDescription> namesAndDescriptions;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Price> prices;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        prices.forEach(x -> x.setProduct(this));
        this.prices = prices;

    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Set<NameAndDescription> getNamesAndDescriptions() {
        return namesAndDescriptions;
    }

    public void setNamesAndDescriptions(Set<NameAndDescription> namesAndDescriptions) {
        namesAndDescriptions.forEach(x -> x.setProduct(this));
        this.namesAndDescriptions = namesAndDescriptions;
    }

    public void updateFromProduct(Product product) {
        setNamesAndDescriptions(product.getNamesAndDescriptions());
        setPrices(product.getPrices());
    }
}
