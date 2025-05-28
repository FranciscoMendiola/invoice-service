package com.invoice.api.dto.Out;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelo de Producto con un ID, gtin, nombre, descripción, precio, stock y
 * estado.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DtoProductValidateOut {
	

	@NotNull(message = "El gtin es obligatorio")
	@Pattern(regexp = "^\\+?\\d{13}$", message = "El gtin tiene un formato inválido")
	@JsonProperty("gtin")
	private String gtin;

	@NotNull(message="El product es obligatorio")
	@JsonProperty("product")
	private String product;

	@NotNull(message="El price es obligatorio")
	@Min(value = 0, message = "El precio no puede ser negativo")
	@JsonProperty("price")
	private Double price;

	@NotNull(message="Quantity es obligatorio")
	@Min(value = 0, message = "Quantity no puede ser negativo")
	@JsonProperty("quantity")
	private Integer quantity;
}
