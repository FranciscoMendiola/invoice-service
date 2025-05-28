package com.invoice.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@CompoundIndexes({
    @CompoundIndex(name = "ux_customer_product", def = "{'customerId': 1, 'productId': 1}", unique = true)
})
@Document(collection = "cart_item")
public class CartItem {

    @Id
    @JsonProperty("cartItemId")
    private String cartItemId;

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
