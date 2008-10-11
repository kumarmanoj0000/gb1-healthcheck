package com.gb1.healthcheck.domain.foods;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("simpleFoodCreationValidator")
public class FullSimpleFoodCreationValidator implements Validator<SimpleFood, FoodException> {
	@Resource
	protected FoodRepository foodRepo;

	public FullSimpleFoodCreationValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		if (foodRepo.findFoodByName(food.getName()) != null) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}
}
