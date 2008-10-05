package com.gb1.healthcheck.web.meals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class MealBuilder implements Serializable {
	private Meal source;
	private Date instant;
	private List<PreparedFoodBuilder> dishBuilders = new ArrayList<PreparedFoodBuilder>();

	public MealBuilder(Meal source) {
		this.source = source;
		this.instant = source.getInstant();

		for (PreparedFood dish : source.getDishes()) {
			dishBuilders.add(new PreparedFoodBuilder(dish));
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

	public List<PreparedFoodBuilder> getDishes() {
		return dishBuilders;
	}

	public void addDish() {
		dishBuilders.add(new PreparedFoodBuilder());
		System.err.println("Added dish: dishBuilders.size=" + dishBuilders.size());
	}

	public void removeDish(int dishIndex) {
		dishBuilders.remove(dishIndex);
		System.err.println("Removed dish: dishBuilders.size=" + dishBuilders.size());
	}

	public Meal build(FoodService foodService) {
		source.setInstant(instant);

		source.clearDishes();
		for (PreparedFoodBuilder dishBuilder : dishBuilders) {
			source.addDish(dishBuilder.build(foodService));
		}

		return source;
	}
}
