package com.gb1.healthcheck.domain.meals;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import com.gb1.commons.Identifiable;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.users.User;

@Entity
public class Meal implements Identifiable, Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private User eater;

	private Date instant;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinTable(name = "MEAL_DISHES", joinColumns = { @JoinColumn(name = "meal_id") }, inverseJoinColumns = { @JoinColumn(name = "dish_id") })
	private Set<PreparedFood> dishes = new HashSet<PreparedFood>();

	/**
	 * Package-protected constructor for JPA.
	 */
	Meal() {
	}

	Meal(Long id, User eater, Date instant) {
		this.id = id;
		this.eater = eater;
		setInstant(instant);
	}

	public Meal(User eater, Date instant) {
		this(null, eater, instant);
	}

	public Long getId() {
		return id;
	}

	public User getEater() {
		return eater;
	}

	public Date getInstant() {
		return new Date(instant.getTime());
	}

	public void setInstant(Date instant) {
		this.instant = new Date(instant.getTime());
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

	public boolean containsDish(PreparedFood dish) {
		for (PreparedFood candidate : dishes) {
			if (candidate.equals(dish)) {
				return true;
			}
		}

		return false;
	}

	public Set<PreparedFood> getDishes() {
		return Collections.unmodifiableSet(dishes);
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
