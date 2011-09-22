package org.osmsurround.ra.search;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SearchModelValidator.class)
@Documented
public @interface ValidSearchModel {

	String message() default "SearchModel";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
