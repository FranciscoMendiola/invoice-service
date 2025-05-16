package com.invoice.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.invoice.api.dto.In.DtoCartItemQuantityIn;
import com.invoice.api.entity.CartItem;
import com.invoice.api.repository.RepoCartItem;
import com.invoice.common.dto.ApiResponse;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;

@Service
public class SvcCartItemImp implements SvcCartItem {

    @Autowired
    private RepoCartItem repo;

    @Override
    public ResponseEntity<List<CartItem>> getCartItems(Integer customerId) {
        try {
            List<CartItem> cartItems = repo.findByCustomerId(customerId);
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createCartItem(CartItem in) {
        try {
            if (in.getCartItemId() != null)
                throw new ApiException(HttpStatus.BAD_REQUEST, "El id es generado automáticamente");
            // Guardar el nuevo CartItem

            repo.save(in);
            // Retornar respuesta exitosa con código 201 (CREATED)
            return new ResponseEntity<>(new ApiResponse("El artículo ha sido agregado al carrito exitosamente"),
                    HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String msg = e.getLocalizedMessage();
            if (msg != null) {
                if (msg.contains("ux_customer_product"))
                    throw new ApiException(HttpStatus.CONFLICT, "El articulo ya esta en el carrito");
            }
            // En caso de error en acceso a datos, lanzar excepción personalizada
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateItemQuantity(String cartItemId, DtoCartItemQuantityIn in) {
        try {
            CartItem cartItem = validateCartItemId(cartItemId);

            cartItem.setQuantity(cartItem.getQuantity() + in.getQuantity());

            if (cartItem.getQuantity() <= 0) {
                repo.deleteById(cartItemId);
                return new ResponseEntity<>(new ApiResponse("El articulo ha sido eliminado"), HttpStatus.OK);
            }

            repo.save(cartItem);
            return new ResponseEntity<>(new ApiResponse("La cantidad ha sido actualizada"), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }

    }

    @Override
    public ResponseEntity<ApiResponse> deleteCartItem(String cartItemId) {
        try {
            repo.deleteById(cartItemId);

            return new ResponseEntity<>(new ApiResponse("El articulo ha sido eliminado"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCartItems(Integer customerId) {
        try {
            // Eliminar todos los CartItem con el customerId dado
            repo.deleteByCustomerId(customerId);

            // Devolver respuesta de éxito con código 200 (OK)
            return new ResponseEntity<>(new ApiResponse("Los artículos del carrito han sido eliminados"),
                    HttpStatus.OK);
        } catch (DataAccessException e) {
            // En caso de error en acceso a datos, lanzar excepción personalizada
            throw new DBAccessException(e);
        }
    }

    private CartItem validateCartItemId(String cartItemId) {
        try {
            CartItem cartItem = repo.findById(cartItemId)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id del articulo no existe"));
            return cartItem;
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}
