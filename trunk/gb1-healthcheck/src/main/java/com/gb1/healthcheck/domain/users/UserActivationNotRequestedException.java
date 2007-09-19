package com.gb1.healthcheck.domain.users;

/**
 * Exception thrown when activation is attempted for a user whose activation has not yet been
 * requested.
 * 
 * @author Guillaume Bilodeau
 */
public class UserActivationNotRequestedException extends UserActivationException {
}
