package com.invoice.api.dto.In;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class DtoCartItemQuantityIn {

    @NotNull(message = "Quantity es obligatorio")
    @JsonProperty("quantity")
    private Integer quantity;
}
