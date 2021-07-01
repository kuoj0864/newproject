package io.agileintelligence.ppmtool.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UsernameAlreadyExistsResponse {

	private String username;
}
