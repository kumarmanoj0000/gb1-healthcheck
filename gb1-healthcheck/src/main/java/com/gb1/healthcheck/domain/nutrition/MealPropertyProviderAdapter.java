package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable("mealPropertyProviderAdapter")
public class MealPropertyProviderAdapter implements MealPropertyProvider {
	private FoodRepository foodRepo;
	private MealCreationRequest request;

	public MealPropertyProviderAdapter(MealCreationRequest request) {
		this.request = request;
	}

	public Set<PreparedFood> getDishes() {
		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		for (PreparedFoodCreationRequest dcr : request.getDishCreationRequests()) {
			Food ingredient = foodRepo.loadFood(dcr.getIngredientId());
			dishes.add(new PreparedFood(ingredient, dcr.getPreparationMethod()));
		}

		return dishes;
	}

	public Date getInstant() {
		return request.getInstant();
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
