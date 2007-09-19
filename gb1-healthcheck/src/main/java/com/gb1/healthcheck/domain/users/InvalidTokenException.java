package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when the provided activation token is different from the one assigned to the
 * user.
 * 
 * @author Guillaume Bilodeau
 */
public class InvalidTokenException extends UserActivationException {
}
