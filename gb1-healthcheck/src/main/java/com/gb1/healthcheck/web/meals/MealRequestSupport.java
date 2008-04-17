package com.gb1.healthcheck.web.meals;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public abstract class MealRequestSupport {
	private Long mealId;
	private Long eaterId;
	private Date instant;

	private Map<String, BasicPreparedFoodRequest> pfRequests = new LinkedHashMap<String, BasicPreparedFoodRequest>();

	protected MealRequestSupport() {
	}

	public MealRequestSupport(User eater) {
		eaterId = eater.getId();
	}

	public MealRequestSupport(Meal meal) {
		mealId = meal.getId();
		eaterId = meal.getEater().getId();
		instant = meal.getInstant();

		for (PreparedFood dish : meal.getDishes()) {
			addDish(dish);
		}
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public Long getEaterId() {
		return eaterId;
	}

	public void setEaterId(Long eaterId) {
		this.eaterId = eaterId;
	}

	public Date getInstant() {
		return instant;
	}

	@TypeConversion(converter = "com.gb1.healthcheck.web.meals.MealInstantConverter")
	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Map<String, BasicPreparedFoodRequest> getDishes() {
		return pfRequests;
	}

	public void setDishes(Map<String, BasicPreparedFoodRequest> dishes) {
		pfRequests.clear();
		pfRequests.putAll(dishes);
	}

	protected void addDish(PreparedFood dish) {
		pfRequests.put(Integer.toString(pfRequests.size()), new BasicPreparedFoodRequest(dish
				.getIngredient().getId(), dish.getPreparationMethod()));
	}
}
