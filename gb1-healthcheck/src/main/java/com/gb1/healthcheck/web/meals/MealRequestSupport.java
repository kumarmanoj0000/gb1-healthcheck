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

	private Map<Integer, BasicPreparedFoodRequest> dishRequests = new LinkedHashMap<Integer, BasicPreparedFoodRequest>();

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

	public Map<Integer, BasicPreparedFoodRequest> getDishes() {
		return dishRequests;
	}

	public void setDishes(Map<Integer, BasicPreparedFoodRequest> dishes) {
		dishRequests.clear();
		dishRequests.putAll(dishes);
	}

	protected void addDish(PreparedFood dish) {
		dishRequests.put(dishRequests.size(), new BasicPreparedFoodRequest(dish.getIngredient()
				.getId(), dish.getPreparationMethod()));
	}
}
