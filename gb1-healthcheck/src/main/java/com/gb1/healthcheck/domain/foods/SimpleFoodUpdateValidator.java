package com.gb1.healthcheck.domain.foods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("simpleFoodUpdateValidator")
public class SimpleFoodUpdateValidator implements Validator<SimpleFood, FoodException> {
	@Resource
	protected FoodRepository foodRepo;

	public SimpleFoodUpdateValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		isFoodNameAlreadyTaken(food);
	}

	private void isFoodNameAlreadyTaken(SimpleFood food) throws FoodAlreadyExistsException {
		// make sure that no other food has the same name, aside from the validated food itself

		boolean nameAlreadyTaken;
		List<Food> foodsWithName = foodRepo.findFoodsByName(food.getName());

		if (foodsWithName.isEmpty()) {
			nameAlreadyTaken = false;
		}
		else if (foodsWithName.size() == 1) {
			Food foodWithSameName = foodsWithName.iterator().next();
			nameAlreadyTaken = !foodWithSameName.getId().equals(food.getId());
		}
		else {
			nameAlreadyTaken = true;
		}

		if (nameAlreadyTaken) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}
}
