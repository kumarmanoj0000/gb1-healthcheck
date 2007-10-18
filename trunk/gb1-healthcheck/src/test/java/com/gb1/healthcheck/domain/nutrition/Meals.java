package com.gb1.healthcheck.domain.nutrition;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

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

	public static List<Meal> mealHistory() {
		final List<Meal> mealHistory = new LinkedList<Meal>();

		mealHistory.add(new Meal(parseInstant("2007-10-13 16:00")).addDish(Meals.redWineDrink()));
		mealHistory.add(new Meal(parseInstant("2007-10-13 18:30")).addDish(Meals.spaghettiDish()));
		mealHistory.add(new Meal(parseInstant("2007-10-14 18:00")).addDish(Meals.spaghettiDish())
				.addDish(Meals.redWineDrink()));

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
