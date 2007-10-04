package com.gb1.healthcheck.web.nutrition;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;

@Configurable("complexFoodCreationRequest")
public class ComplexFoodCreationRequest extends ComplexFoodRequestSupport implements
		ComplexFoodPropertyProvider {
	public ComplexFoodCreationRequest() {
	}
}
