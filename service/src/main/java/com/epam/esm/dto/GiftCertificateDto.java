package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto {
    private Long id;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotNull(message = "description must not be null")
    @NotEmpty(message = "description must not be empty")
    private String description;

    @NotNull(message = "price must not be null")
    @Positive(message = "price must be positive")
    private Double price;

    @NotNull(message = "duration must not be null")
    @Positive(message = "duration must be positive")
    private Integer duration;

    @JsonIgnore
    private String createDate;

    @JsonIgnore
    private String lastUpdateDate;

    private TagDto[] tags;
}