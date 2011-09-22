package org.osmsurround.ra.search;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class SearchModelValidator implements ConstraintValidator<ValidSearchModel, SearchModel> {

	@Override
	public void initialize(ValidSearchModel constraintAnnotation) {
	}

	@Override
	public boolean isValid(SearchModel searchModel, ConstraintValidatorContext context) {

		if (searchModel == null)
			return false;

		if (hasValidText(searchModel.getName()))
			return true;
		if (hasValidText(searchModel.getOperator()))
			return true;
		if (hasValidText(searchModel.getRelationType()))
			return true;
		if (hasValidText(searchModel.getRef()))
			return true;
		if (hasValidText(searchModel.getNetwork()))
			return true;
		if (hasValidText(searchModel.getRoute()))
			return true;

		return false;
	}

	private boolean hasValidText(String value) {
		if (StringUtils.hasText(value)) {
			return StringUtils.hasText(value.replaceAll("%", ""));
		}
		return false;
	}
}
