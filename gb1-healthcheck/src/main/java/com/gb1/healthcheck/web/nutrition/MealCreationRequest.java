package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.MealPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;

@Configurable("mealCreationRequest")
public class MealCreationRequest implements MealPropertyProvider {
	private FoodRepository foodRepo;

	private Date dateAndTime;
	private List<Long> selectedFoodIds = new LinkedList<Long>();
	private List<String> prepMethods = new LinkedList<String>();

	public MealCreationRequest() {
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public Set<PreparedFood> getDishes() {
		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		for (int i = 0; i < selectedFoodIds.size(); i++) {
			Food food = foodRepo.loadFood(selectedFoodIds.get(i));
			PreparationMethod prepMethod = PreparationMethod.valueOf(prepMethods.get(i));
			dishes.add(new PreparedFood(food, prepMethod));
		}

		return Collections.unmodifiableSet(dishes);
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

	public void setSelectedPreparationMethods(String[] prepMethodNames) {
		prepMethods.clear();
		for (String prepMethodName : prepMethodNames) {
			prepMethods.add(prepMethodName);
		}
	}

	public String[] getSelectedPreparationMethodNames() {
		return prepMethods.toArray(new String[0]);
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
