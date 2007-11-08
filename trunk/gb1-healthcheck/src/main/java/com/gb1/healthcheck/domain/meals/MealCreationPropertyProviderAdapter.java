package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;

@Configurable("mealCreationPropertyProviderAdapter")
public class MealCreationPropertyProviderAdapter implements MealCreationPropertyProvider {
	private FoodRepository foodRepo;
	private MealCreationRequest request;

	public MealCreationPropertyProviderAdapter(MealCreationRequest request) {
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
