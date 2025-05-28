package com.invoice.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.invoice.api.dto.In.DtoCartItemIn;
import com.invoice.api.dto.In.DtoCartItemQuantityIn;
import com.invoice.api.entity.CartItem;
import com.invoice.common.dto.ApiResponse;

public interface SvcCartItem {

    public ResponseEntity<List<CartItem>> getCartItems();
    public ResponseEntity<ApiResponse> createCartItem(DtoCartItemIn in);
    public ResponseEntity<ApiResponse> updateItemQuantity(String cartItemId, DtoCartItemQuantityIn in);
    public ResponseEntity<ApiResponse> deleteCartItem(String cartItemId);
    public ResponseEntity<ApiResponse> deleteCartItems();
} 
