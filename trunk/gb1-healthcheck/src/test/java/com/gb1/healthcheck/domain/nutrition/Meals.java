package com.gb1.healthcheck.domain.nutrition;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

	public static List<Meal> mealHistory() {
		final List<Meal> mealHistory = new LinkedList<Meal>();

		mealHistory.add(new Meal(parseDateAndTime("2007-10-13 16:00"))
				.addDish(Meals.redWineDrink()));
		mealHistory.add(new Meal(parseDateAndTime("2007-10-13 18:30")).addDish(Meals
				.spaghettiDish()));
		mealHistory.add(new Meal(parseDateAndTime("2007-10-14 18:00")).addDish(
				Meals.spaghettiDish()).addDish(Meals.redWineDrink()));

		return mealHistory;
	}

	private static Date parseDateAndTime(String text) {
		Date dateAndTime = null;

		try {
			dateAndTime = DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
		}
		catch (ParseException e) {
		}

		return dateAndTime;
	}
}
