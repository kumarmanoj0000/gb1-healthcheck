package org.apache.struts2.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that identifies a method to provide with a parameter corresponding to the currently
 * authenticated user. Inspired by Practical Apache Struts2 Web 2.0 Projects by Ian Roughley.
 * 
 * @author Guillaume Bilodeau
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUser {
}
