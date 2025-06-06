package com.invoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.api.dto.In.DtoCartItemIn;
import com.invoice.api.dto.In.DtoCartItemQuantityIn;
import com.invoice.api.entity.CartItem;
import com.invoice.api.service.SvcCartItem;
import com.invoice.common.dto.ApiResponse;
import com.invoice.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
@Tag(name = "Cart", description = "Gestión de carritos")
@RequestMapping("/cart-item")
public class CtrlCartItem {

    @Autowired
    private SvcCartItem svc;

    @GetMapping
    @Operation(summary = "Consultar artículos", description = "Retorna una lista con todos los artículos del carrito de un usuario")
    public ResponseEntity<List<CartItem>> getCartItems() {
        return svc.getCartItems();
    }

    @PostMapping
	@Operation(summary = "Registrar artículos", description = "Registra un nuevo artículo al carrito de un usuario")
    public ResponseEntity<ApiResponse> createCartItem(@Valid @RequestBody DtoCartItemIn in,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());	

        return svc.createCartItem(in);
    }


    @PatchMapping("/{cartItemId}/quantity")
    @Operation(summary = "Actualizar cantidad de artículos", description = "Actualiza la cantidad de un artículo en el carrito de un usuario")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(@Valid @PathVariable("cartItemId") String cartItemId,
            @Valid @RequestBody DtoCartItemQuantityIn in) {
        return svc.updateItemQuantity(cartItemId, in);
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Eliminar articulo por id", description = "Elimina un artículo del carrito de un usuario")
    public ResponseEntity<ApiResponse> deleteCartItem(@Valid @PathVariable("cartItemId") String cartItemId) {
        return svc.deleteCartItem(cartItemId);
    }

    @DeleteMapping
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los artículos del carrito de un usuario")
    public ResponseEntity<ApiResponse> deleteCartItems() {
        return svc.deleteCartItems();
    }
}