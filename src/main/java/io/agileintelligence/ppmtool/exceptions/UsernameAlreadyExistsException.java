package io.agileintelligence.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = -2119609489166670384L;

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}
