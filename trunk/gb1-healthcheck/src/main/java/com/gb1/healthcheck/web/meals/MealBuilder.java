package com.gb1.healthcheck.web.meals;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class MealBuilder {
	private Meal source;

	private Date instant;
	private Map<Integer, PreparedFoodBuilder> dishBuilders = new LinkedHashMap<Integer, PreparedFoodBuilder>();

	public MealBuilder(Meal source) {
		this.source = source;

		instant = source.getInstant();

		for (PreparedFood dish : source.getDishes()) {
			dishBuilders.put(dishBuilders.size(), new PreparedFoodBuilder(dish));
		}
	}

	public Long getId() {
		return source.getId();
	}

	public User getEater() {
		return source.getEater();
	}

	public Date getInstant() {
		return instant;
	}

	@TypeConversion("com.gb1.healthcheck.web.meals.MealInstantConverter")
	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Map<Integer, PreparedFoodBuilder> getDishes() {
		return dishBuilders;
	}

	public Meal build(FoodService foodService) {
		source.setInstant(instant);

		source.clearDishes();
		for (PreparedFoodBuilder dishBuilder : dishBuilders.values()) {
			source.addDish(dishBuilder.build(foodService));
		}

		return source;
	}
}
