package com.cart.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Document(collection = "cart_items")
public class CartItem {

    @Id
    @JsonProperty("cartItemId")
    private String cartItemId;

    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("productId")
    private Integer productId;

    @JsonProperty("quantity")
    @Min(value = 1, message = "La cantidad no puede ser negativa")
    private Integer quantity;
}
