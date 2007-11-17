package com.gb1.healthcheck.domain.meals;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.users.Users;

public class Meals {
	private Meals() {
	}

	public static PreparedFood spaghettiDish() {
		return new PreparedFood(Foods.spaghetti(), PreparationMethod.STEWED);
	}

	public static PreparedFood redWineDrink() {
		return new PreparedFood(Foods.redWine(), PreparationMethod.RAW);
	}

	public static PreparedFood waterDrink() {
		return new PreparedFood(Foods.water(), PreparationMethod.RAW);
	}

	public static Set<PreparedFood> allDishes() {
		final Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		dishes.add(spaghettiDish());
		dishes.add(redWineDrink());

		return Collections.unmodifiableSet(dishes);
	}

	public static Meal fullItalianDinner() {
		return new Meal(1L, Users.gb(), parseInstant("2007-10-13 18:00")).addDish(
				Meals.spaghettiDish()).addDish(Meals.redWineDrink());
	}

	public static List<Meal> mealHistory() {
		final List<Meal> mealHistory = new LinkedList<Meal>();

		mealHistory.add(fullItalianDinner());
		mealHistory.add(new Meal(2L, Users.gb(), parseInstant("2007-10-14 16:00")).addDish(Meals
				.redWineDrink()));
		mealHistory.add(new Meal(3L, Users.gb(), parseInstant("2007-10-14 18:30")).addDish(Meals
				.spaghettiDish()));

		return mealHistory;
	}

	private static Date parseInstant(String text) {
		Date instant = null;

		try {
			instant = DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
		}
		catch (ParseException e) {
		}

		return instant;
	}
}
