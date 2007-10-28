package com.gb1.healthcheck.web.nutrition;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;

@Configurable("complexFoodUpdateRequest")
public class BasicComplexFoodUpdateRequest extends ComplexFoodRequestSupport {
	public BasicComplexFoodUpdateRequest(ComplexFood food) {
		setName(food.getName());
		setIngredients(food.getIngredients());
	}
}
