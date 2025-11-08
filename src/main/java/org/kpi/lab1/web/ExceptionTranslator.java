package org.kpi.lab1.web;

import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.featuretoggle.exception.DisabledFeatureToggleException;
import org.kpi.lab1.web.exception.ParamsViolationDetails;
import org.kpi.lab1.web.exception.ProductNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static org.kpi.lab1.util.ProductDetailsUtils.getValidationErrorsProblemDetail;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ProductNotFoundException.class)
  ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
    log.info("Product exception was not raised");
    ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
    problemDetail.setType(URI.create("product-not-found"));
    problemDetail.setTitle("Product not found");
    return problemDetail;
  }

  @ExceptionHandler(DisabledFeatureToggleException.class)
  ProblemDetail handleDisabledFeatureToggleException(DisabledFeatureToggleException ex) {
    log.info("Disabled feature exception was not raised");
    ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
    problemDetail.setType(URI.create("feature-toggle-disabled"));
    problemDetail.setTitle("Feature is disabled");
    return problemDetail;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    List<ParamsViolationDetails> validationResponse =
        errors.stream()
            .map(
                err ->
                    ParamsViolationDetails.builder()
                        .reason(err.getDefaultMessage())
                        .fieldName(err.getField())
                        .build())
            .toList();
    log.info("Input params validation failed");
    return ResponseEntity.status(BAD_REQUEST)
        .body(getValidationErrorsProblemDetail(validationResponse));
  }
}
