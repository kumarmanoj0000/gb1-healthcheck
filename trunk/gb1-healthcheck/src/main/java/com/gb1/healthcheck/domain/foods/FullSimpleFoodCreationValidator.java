package com.gb1.healthcheck.domain.foods;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("simpleFoodCreationValidator")
public class FullSimpleFoodCreationValidator implements SimpleFoodValidator {
	private FoodRepository foodRepo;

	public FullSimpleFoodCreationValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		if (foodRepo.findFoodByName(food.getName()) != null) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}

	@Resource
	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
