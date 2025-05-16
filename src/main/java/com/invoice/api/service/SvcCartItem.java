package com.invoice.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.invoice.api.dto.In.DtoCartItemQuantityIn;
import com.invoice.api.entity.CartItem;
import com.invoice.common.dto.ApiResponse;

public interface SvcCartItem {

    public ResponseEntity<List<CartItem>> getCartItems(Integer customerId);
    public ResponseEntity<ApiResponse> createCartItem(CartItem in);
    public ResponseEntity<ApiResponse> updateItemQuantity(String cartItemId, DtoCartItemQuantityIn in);
    public ResponseEntity<ApiResponse> deleteCartItem(String cartItemId);
    public ResponseEntity<ApiResponse> deleteCartItems(Integer customerId);
} 
