package com.setronica.intern.test.project.dto.response;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientResponseTranslationDTO {
    @NotNull
    @NotBlank
    @Length(max = 255)
    private String name;

    @Length(max = 255)
    private String description;

    @Id
    @NotNull
    @NotBlank
    private String language;

    public ClientResponseTranslationDTO(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public ClientResponseTranslationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
