package io.tracee.contextlogger.contextprovider.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures if annotated class or method should be outputted in string representation builder output.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface ProfileConfig {

	boolean basic() default false;

	boolean enhanced() default false;

	boolean full() default true;

}
