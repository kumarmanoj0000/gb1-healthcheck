package com.gb1.healthcheck.web.meals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public abstract class MealRequestSupport {
	private Date instant;
	private List<Long> selectedFoodIds = new LinkedList<Long>();
	private List<String> selectedPrepMethodNames = new LinkedList<String>();

	public MealRequestSupport() {
	}

	public Date getInstant() {
		return instant;
	}

	@TypeConversion(converter = "com.gb1.healthcheck.web.meals.MealInstantConverter")
	public void setInstant(Date instant) {
		this.instant = instant;
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
}
