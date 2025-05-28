package com.invoice.common.mapper;

import org.springframework.stereotype.Service;

import com.invoice.api.dto.In.DtoCartItemIn;
import com.invoice.api.entity.CartItem;

@Service
public class MapperCartItem {


    public CartItem fomCartItem(DtoCartItemIn in) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(in.getUserId());
        cartItem.setProductId(in.getProductId());
        cartItem.setQuantity(in.getQuantity());
        
        return cartItem;
    }
}
