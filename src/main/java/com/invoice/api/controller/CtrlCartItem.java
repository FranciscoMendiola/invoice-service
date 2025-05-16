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

import com.invoice.api.dto.In.DtoCartItemQuantityIn;
import com.invoice.api.entity.CartItem;
import com.invoice.api.service.SvcCartItem;
import com.invoice.common.dto.ApiResponse;
import com.invoice.exception.ApiException;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
@RequestMapping("/cart-item")
public class CtrlCartItem {

    @Autowired
    private SvcCartItem svc;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("customerId") Integer customerId) {
        return svc.getCartItems(customerId);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCartItem(@Valid @RequestBody CartItem in,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());	

        return svc.createCartItem(in);
    }

    @PatchMapping("/{cartItemId}/quantity")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(@Valid @PathVariable("cartItemId") String cartItemId,
            @Valid @RequestBody DtoCartItemQuantityIn in) {
        return svc.updateItemQuantity(cartItemId, in);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@Valid @PathVariable("cartItemId") String cartItemId) {
        return svc.deleteCartItem(cartItemId);
    }

    @DeleteMapping("/{customerId}/customer")
    public ResponseEntity<ApiResponse> deleteCartItems(@Valid @PathVariable("customerId") Integer cartId) {
        return svc.deleteCartItems(cartId);
    }
}