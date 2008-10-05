package com.gb1.healthcheck.domain.meals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import com.gb1.healthcheck.core.Identifiable;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.users.User;

@Entity
public class Meal implements Identifiable, Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "eater_id", nullable = false)
	private User eater;

	private Date instant = new Date();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinTable(name = "MEAL_DISHES", joinColumns = { @JoinColumn(name = "meal_id") }, inverseJoinColumns = { @JoinColumn(name = "dish_id") })
	private List<PreparedFood> dishes = new ArrayList<PreparedFood>();

	public Meal() {
	}

	public Long getId() {
		return id;
	}

	Meal setId(Long id) {
		this.id = id;
		return this;
	}

	public User getEater() {
		return eater;
	}

	public Meal setEater(User eater) {
		this.eater = eater;
		return this;
	}

	public Date getInstant() {
		return new Date(instant.getTime());
	}

	public Meal setInstant(Date instant) {
		this.instant = new Date(instant.getTime());
		return this;
	}

	public Meal addDish(PreparedFood dish) {
		Validate.notNull(dish);
		dishes.add(dish);

		return this;
	}

	public Meal removeDish(PreparedFood dish) {
		Validate.notNull(dish);
		dishes.remove(dish);

		return this;
	}

	public Meal clearDishes() {
		dishes.clear();
		return this;
	}

	public boolean containsDish(PreparedFood dish) {
		for (PreparedFood candidate : dishes) {
			if (candidate.equals(dish)) {
				return true;
			}
		}

		return false;
	}

	public List<PreparedFood> getDishes() {
		return Collections.unmodifiableList(dishes);
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
		EqualsBuilder builder = new EqualsBuilder().append(this.getEater(), that.getEater())
				.append(this.getInstant(), that.getInstant());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getEater()).append(
				this.getInstant());
		return builder.toHashCode();
	}

	public static class ByInstantComparator implements Comparator<Meal> {
		public int compare(Meal meal1, Meal meal2) {
			CompareToBuilder builder = new CompareToBuilder().append(meal1.getInstant(), meal2
					.getInstant());
			return builder.toComparison();
		}
	}
}
