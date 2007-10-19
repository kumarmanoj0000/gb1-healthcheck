package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.MealPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;

public class MealRequestSupport implements MealPropertyProvider {
	private FoodRepository foodRepo;

	private Date instant;
	private List<Long> selectedFoodIds = new LinkedList<Long>();
	private List<String> selectedPrepMethodNames = new LinkedList<String>();

	public MealRequestSupport() {
	}

	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Set<PreparedFood> getDishes() {
		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		for (int i = 0; i < selectedFoodIds.size(); i++) {
			Food food = foodRepo.loadFood(selectedFoodIds.get(i));
			PreparationMethod prepMethod = PreparationMethod
					.valueOf(selectedPrepMethodNames.get(i));
			dishes.add(new PreparedFood(food, prepMethod));
		}

		return Collections.unmodifiableSet(dishes);
	}

	protected void setDishes(Set<PreparedFood> dishes) {
		selectedFoodIds.clear();
		selectedPrepMethodNames.clear();

		for (PreparedFood dish : dishes) {
			selectedFoodIds.add(dish.getIngredient().getId());
			selectedPrepMethodNames.add(dish.getPreparationMethod().name());
		}
	}

	public void setSelectedFoodIds(Long[] foodIds) {
		selectedFoodIds.clear();
		for (Long foodId : foodIds) {
			selectedFoodIds.add(foodId);
		}
	}

	public Long[] getSelectedFoodIds() {
		return selectedFoodIds.toArray(new Long[0]);
	}

	public void setSelectedPreparationMethodNames(String[] prepMethodNames) {
		selectedPrepMethodNames.clear();
		for (String prepMethodName : prepMethodNames) {
			selectedPrepMethodNames.add(prepMethodName);
		}
	}

	public String[] getSelectedPreparationMethodNames() {
		return selectedPrepMethodNames.toArray(new String[0]);
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
