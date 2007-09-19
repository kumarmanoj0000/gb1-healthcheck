package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Meal {
	private Date time;
	private Set<PreparedFood> dishes = new HashSet<PreparedFood>();
	private Set<PreparedFood> drinks = new HashSet<PreparedFood>();

	public Meal(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return new Date(time.getTime());
	}

	public void addDish(PreparedFood dish) {
		Validate.notNull(dish);
		dishes.add(dish);
	}

	public void addDrink(PreparedFood drink) {
		Validate.notNull(drink);
		drinks.add(drink);
	}

	public boolean containsFood(Food food) {
		for (PreparedFood dish : dishes) {
			if (dish.containsIngredient(food)) {
				return true;
			}
		}

		for (PreparedFood drink : drinks) {
			if (drink.containsIngredient(food)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsGroup(Group group) {
		for (PreparedFood dish : dishes) {
			if (dish.containsGroup(group)) {
				return true;
			}
		}

		for (PreparedFood drink : drinks) {
			if (drink.containsGroup(group)) {
				return true;
			}
		}

		return false;
	}

	public boolean isSourceOfNutrient(Nutrient nutrient) {
		for (PreparedFood dish : dishes) {
			if (dish.isSourceOfNutrient(nutrient)) {
				return true;
			}
		}

		for (PreparedFood drink : drinks) {
			if (drink.isSourceOfNutrient(nutrient)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Meal)) {
			return false;
		}

		Meal that = (Meal) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getTime(), that.getTime());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getTime());
		return builder.toHashCode();
	}
}
