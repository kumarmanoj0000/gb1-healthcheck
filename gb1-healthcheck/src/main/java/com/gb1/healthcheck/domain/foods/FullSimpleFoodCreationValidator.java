package com.gb1.healthcheck.domain.foods;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("simpleFoodCreationValidator")
public class FullSimpleFoodCreationValidator implements SimpleFoodValidator {
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
