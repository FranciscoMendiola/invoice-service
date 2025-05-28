package com.invoice.api.dto.Out;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DtoProductQuantityOut {
    
    @NotNull(message="Quantity es obligatorio")
	@Min(value = 0, message = "Quantity no puede ser negativo")
	@JsonProperty("quantity")
	private Integer quantity;
}
