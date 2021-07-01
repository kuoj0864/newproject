package io.agileintelligence.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	public final ResponseEntity<?> handleProjectIdException(ProjectIdException ex, WebRequest request) {
		ProjectIdExceptionResponse exceptionResponse = ProjectIdExceptionResponse.builder().projectIdentifier(ex.getMessage()).build();
		return new ResponseEntity<ProjectIdExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
		ProjectNotFoundExceptionResponse exceptionResponse = ProjectNotFoundExceptionResponse.builder().projectNotFound(ex.getMessage()).build();
		return new ResponseEntity<ProjectNotFoundExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<?> handleUserAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
		UsernameAlreadyExistsResponse exceptionResponse = UsernameAlreadyExistsResponse.builder().username(ex.getMessage()).build();
		return new ResponseEntity<UsernameAlreadyExistsResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
