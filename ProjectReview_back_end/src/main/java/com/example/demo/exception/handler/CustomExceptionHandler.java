package com.example.demo.exception.handler;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.exception.IdAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.StatusDeletedException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.exception.invalidParameterException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler {
	
	@SuppressWarnings("unchecked")
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<JSONObject> handleAuthenticationException(HttpClientErrorException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();
		
		response.put("code", "AUTHERRCOD");
		response.put("message", "Incorrect Password");
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<JSONObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			JSONObject detail = new JSONObject();
			try {
				detail.put(((FieldError) error).getField(), error.getDefaultMessage());
				details.add(detail);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
		response.put("code", "VALERRCOD");
		response.put("message", "Validation Failed");
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	

	@ExceptionHandler(IdAlreadyExistsException.class)
	public ResponseEntity<ServiceResponse>  handleIdAlreadyExistsException(IdAlreadyExistsException e,WebRequest request){
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error  =  new ServiceResponse("FAILED", "Duplicate Id found", details);
		return new ResponseEntity<>(error,HttpStatus.OK);
		
	}
	
	@SuppressWarnings("unchecked")
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<JSONObject>  handleIdAlreadyExistsException(UsernameNotFoundException ex,WebRequest request){
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "VALERRCOD");
		response.put("message", "Username Not Found");
		response.put("details", details);
		response.put("access_token","Username Not Found");

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@ExceptionHandler(invalidParameterException.class)
	public ResponseEntity<JSONObject>  handleInvalidParameterException(invalidParameterException ex,WebRequest request){
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "VALERRCOD");
		response.put("message", ex.getMessage());
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<JSONObject>  handleRecordNotFoundException(RecordNotFoundException ex,WebRequest request){
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "Record Not Found");
		response.put("message", ex.getMessage());
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@ExceptionHandler(StatusDeletedException.class)
	public ResponseEntity<JSONObject>  handleStatusDeletedException(StatusDeletedException ex,WebRequest request){
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "DELETED");
		response.put("message", "Deleted data cannot be modified!!!");
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}
	

		
	
}
