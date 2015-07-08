package io.tracee.contextlogger.contextprovider.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate classes or methods annotated with
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface ProfileSettings {

	boolean basic() default false;

	boolean enhanced() default false;

	boolean full() default true;

}
