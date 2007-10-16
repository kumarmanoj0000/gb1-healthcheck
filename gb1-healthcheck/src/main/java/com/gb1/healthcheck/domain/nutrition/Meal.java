package com.gb1.healthcheck.domain.nutrition;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.commons.Identifiable;

@Entity
public class Meal implements Identifiable {
	@Id
	@GeneratedValue
	private Long id;

	private Date dateAndTime;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MEAL_DISHES", joinColumns = { @JoinColumn(name = "meal_id") }, inverseJoinColumns = { @JoinColumn(name = "dish_id") })
	private Set<PreparedFood> dishes = new HashSet<PreparedFood>();

	Meal() {
		// for JPA
	}

	public Meal(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Long getId() {
		return id;
	}

	public Date getDateAndTime() {
		return new Date(dateAndTime.getTime());
	}

	public Meal addDish(PreparedFood dish) {
		Validate.notNull(dish);
		dishes.add(dish);

		return this;
	}

	public boolean containsFood(Food food) {
		for (PreparedFood dish : dishes) {
			if (dish.containsIngredient(food)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsGroup(FoodGroup foodGroup) {
		for (PreparedFood dish : dishes) {
			if (dish.containsGroup(foodGroup)) {
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
		EqualsBuilder builder = new EqualsBuilder().append(this.getDateAndTime(), that
				.getDateAndTime());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getDateAndTime());
		return builder.toHashCode();
	}

	public static class ByDateAndTimeComparator implements Comparator<Meal> {
		public int compare(Meal meal1, Meal meal2) {
			return meal1.getDateAndTime().compareTo(meal2.getDateAndTime());
		}
	}
}
