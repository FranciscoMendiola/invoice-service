package com.invoice.common.util;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.invoice.exception.ApiException;

@Component
public class JwtDecoder {
	
	public boolean isAdmin() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.isAuthenticated()) {
	            return authentication.getAuthorities()
	                .stream()
	                .anyMatch(authority -> "ADMIN".equals(authority.getAuthority()));
	        }

	        return false;
		}catch(Exception e) {
			System.out.println("El usuario no es administrador");
			return false;
		}
	}
	
	public Integer getUserId() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Map<String, Object> payload = (Map<String, Object>) authentication.getCredentials();

			return (Integer) payload.get("user_id");
		}catch(Exception e) {
			throw new ApiException(HttpStatus.PRECONDITION_FAILED, "El usuario es inválido");
		}
	}
}
