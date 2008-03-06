package com.gb1.healthcheck.services.meals.support;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.meals.MealCreationPropertyProvider;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

@Component("mealCreationPropertyProviderAdapter")
@Scope("prototype")
@Configurable
public class MealCreationPropertyProviderAdapter implements MealCreationPropertyProvider {
	private MealCreationRequest request;

	private FoodRepository foodRepo;
	private UserRepository userRepo;

	public MealCreationPropertyProviderAdapter(MealCreationRequest request) {
		this.request = request;
	}

	public User getEater() {
		User eater = userRepo.loadUser(request.getEaterId());
		return eater;
	}

	public Date getInstant() {
		return request.getInstant();
	}

	public Set<PreparedFood> getDishes() {
		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		for (PreparedFoodCreationRequest dcr : request.getDishCreationRequests()) {
			Food ingredient = foodRepo.loadFood(dcr.getIngredientId());
			dishes.add(new PreparedFood(ingredient, dcr.getPreparationMethod()) {
			});
		}

		return dishes;
	}

	@Resource
	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}

	@Resource
	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
}
