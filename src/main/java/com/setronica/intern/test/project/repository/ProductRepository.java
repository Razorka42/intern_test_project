package com.setronica.intern.test.project.repository;

import com.setronica.intern.test.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    String BASE_QUERY = "Select product " +
            "FROM Product product " +
            "JOIN product.prices prices " +
            "JOIN product.translations translations " +
            "WHERE prices.currency = :currency AND translations.language = :language ";

    String AND_WHERE_SEARCH = "AND (translations.name LIKE %:term% OR translations.description LIKE %:term%)";

    @Query(value = BASE_QUERY)
    Page<Product> findAllProducts(@Param("currency") String currency, @Param("language") String language, Pageable pageable);

    @Query(value = BASE_QUERY + AND_WHERE_SEARCH)
    Page<Product> searchProduct(@Param("currency") String currency, @Param("language") String language,
                                @Param("term") String term, Pageable pageable);

}
