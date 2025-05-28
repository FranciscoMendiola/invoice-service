package com.invoice.api.dto.In;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DtoCartItemIn {

    @NotNull(message="El userId es obligatorio")
    @JsonProperty("userId")
    private Integer userId;

    @NotNull(message="El customerId es obligatorio")
    @JsonProperty("productId")
    private Integer productId;

    @Min(value = 1, message = "La cantidad no puede ser negativa")
    @JsonProperty("quantity")
    private Integer quantity;
}
