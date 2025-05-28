package com.invoice.api.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.dto.Out.DtoProductQuantityOut;
import com.invoice.api.dto.Out.DtoProductValidateOut;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.InvoiceItem;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.common.dto.ApiResponse;
import com.invoice.common.mapper.MapperInvoiceList;
import com.invoice.common.util.JwtDecoder;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	@Autowired
	private RepoInvoice repo;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	private RestTemplate restTemplate; // Inyectar RestTemplate para las consultas a la API

	@Autowired
	private MapperInvoiceList mapper;

	@Value("${product.api.url}") // Obtener directamente de application.properties
	private String productApiUrl;

	@Value("${company.rfc}")
	private String companyRfc;

	@Value("${company.name}")
	private String companyName;

	@Value("${company.postalCode}")
	private String companyPostalCode;

	@Override
	public ResponseEntity<List<DtoInvoiceList>> getInvoices() {
		try {
			if (jwtDecoder.isAdmin()) {
				return new ResponseEntity<>(mapper.toDtoList(repo.findAll()), HttpStatus.OK);
			} else {
				Integer user_id = jwtDecoder.getUserId();
				return new ResponseEntity<>(mapper.toDtoList(repo.findByUser_Id(user_id)), HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<Invoice> getInvoice(String id) {
		try {
			Invoice invoice = repo.findById(id).get();
			if (!jwtDecoder.isAdmin()) {
				Integer user_id = jwtDecoder.getUserId();
				if (invoice.getUser_id() != user_id) {
					throw new ApiException(HttpStatus.FORBIDDEN, "El token no es válido para consultar esta factura");
				}
			}
			return new ResponseEntity<>(invoice, HttpStatus.OK);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (NoSuchElementException e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de la factura no existe");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> createInvoice(HttpServletRequest request, Invoice invoice) {

		// Verificar si los campos que se asignan automáticamente ya tienen valores
		if (invoice.getUser_id() != null) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"El user_id se asigna automáticamente y no debe proporcionarse");
		}
		if (invoice.getIssue_date() != null && !invoice.getIssue_date().isEmpty()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"El issue_date se asigna automáticamente y no debe proporcionarse");
		}
		if ((invoice.getIssuer_rfc() != null && !invoice.getIssuer_rfc().isEmpty())
				&& (companyRfc == null || companyRfc.isEmpty())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"El issuer_rfc se asigna automáticamente y no debe proporcionarse");
		}
		if ((invoice.getIssuer_name() != null && !invoice.getIssuer_name().isEmpty())
				&& (companyName == null || companyName.isEmpty())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"El issuer_name se asigna automáticamente y no debe proporcionarse");
		}
		if ((invoice.getIssuer_pc() != null && !invoice.getIssuer_pc().isEmpty())
				&& (companyPostalCode == null || companyPostalCode.isEmpty())) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"El issuer_pc se asigna automáticamente y no debe proporcionarse");
		}

		// Aquí deberías guardar la factura en tu base de datos...
		invoice.setUser_id(jwtDecoder.getUserId());
		// Asignar datos del emisor desde la configuración
		invoice.setIssuer_rfc(companyRfc);
		invoice.setIssuer_name(companyName);
		invoice.setIssuer_pc(companyPostalCode);

		// Asignar issue_date
		invoice.setIssue_date(LocalDate.now().toString());

		HashMap<Integer, Integer> quantityList = new HashMap<>();
		// Preparar los encabezados con el token Bearer
		HttpHeaders headers = new HttpHeaders();
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			headers.setBearerAuth(token);
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			for (InvoiceItem item : invoice.getItems()) {
				DtoProductValidateOut requestBody = new DtoProductValidateOut();
				requestBody.setGtin(item.getGtin());
				requestBody.setProduct(item.getProduct());
				requestBody.setPrice(item.getUnit_price());
				requestBody.setQuantity(item.getQuantity());

				HttpEntity<DtoProductValidateOut> entity = new HttpEntity<>(requestBody, headers);
				String url = productApiUrl + "/validate";
				ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.POST, entity, Integer.class);

				Integer productId = response.getBody(); // Validación devuelve el ID del producto

				DtoProductQuantityOut reserverQuantity = new DtoProductQuantityOut(-item.getQuantity());
				HttpEntity<DtoProductQuantityOut> entityQuantity = new HttpEntity<>(reserverQuantity, headers);
				url = productApiUrl + "/" + productId + "/stock";
				restTemplate.exchange(url, HttpMethod.PUT, entityQuantity, String.class);

				// Guardar el ID y la cantidad para poder revertir si ocurre un error más
				// adelante
				quantityList.put(productId, item.getQuantity());
			}


			invoice = repo.save(invoice); // Guardar la factura en la base de datos

			return new ResponseEntity<>(new ApiResponse("La factura ha sido registrada"), HttpStatus.CREATED);
		} catch (Exception e) {
			// Revertir los cambios en el stock
			for (Map.Entry<Integer, Integer> entry : quantityList.entrySet()) {
				try {
					Integer productId = entry.getKey();
					Integer quantity = entry.getValue();

					// Devolver la cantidad original al stock
					DtoProductQuantityOut rollbackQuantity = new DtoProductQuantityOut(quantity);
					HttpHeaders rollbackHeaders = new HttpHeaders(headers); // Reusar encabezados
					HttpEntity<DtoProductQuantityOut> rollbackEntity = new HttpEntity<>(rollbackQuantity,
							rollbackHeaders);
					String url = productApiUrl + "/" + productId + "/stock";
					restTemplate.exchange(url, HttpMethod.PATCH, rollbackEntity, String.class);
				} catch (Exception rollbackEx) {
					// Registrar el fallo, pero no lanzar de nuevo
					System.err.println("Error al revertir stock del producto ID: " + entry.getKey());
				}
			}
			if (e instanceof DataAccessException) {
				throw new DBAccessException((DataAccessException) e);
			}
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la factura: " + e.getMessage());
		}
	}

}
