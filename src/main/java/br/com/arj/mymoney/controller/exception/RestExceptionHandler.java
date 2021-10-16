package br.com.arj.mymoney.controller.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.arj.mymoney.exception.BusinessException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BusinessException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
		return createResponse(Arrays.asList(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());

		return createResponse(details, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> createResponse(List<String> details, HttpStatus httpStatusCode) {
		ErrorResponse error = new ErrorResponse("A validação dos dados falhou", details);

		return new ResponseEntity<>(error, httpStatusCode);
	}

}
