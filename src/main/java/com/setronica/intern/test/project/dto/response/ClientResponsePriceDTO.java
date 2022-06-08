package com.setronica.intern.test.project.dto.response;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public final class ClientResponsePriceDTO {
    @NotNull
    @NotBlank
    private String currency;

    @NotNull
    @Positive
    @Digits(integer = 64, fraction = 2)
    private BigDecimal value;

    public ClientResponsePriceDTO(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
