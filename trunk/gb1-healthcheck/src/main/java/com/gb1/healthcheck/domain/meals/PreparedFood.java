package com.gb1.healthcheck.domain.meals;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.commons.Identifiable;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;

@Entity
public class PreparedFood implements Identifiable, Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "food_id")
	private Food ingredient;

	private PreparationMethod preparationMethod;

	PreparedFood() {
		// for JPA
	}

	public PreparedFood(Food ingredient, PreparationMethod preparationMethod) {
		this.ingredient = ingredient;
		this.preparationMethod = preparationMethod;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return ingredient.getName();
	}

	public Food getIngredient() {
		return ingredient;
	}

	public boolean containsIngredient(Food food) {
		boolean contains;

		if (ingredient.equals(food)) {
			contains = true;
		}
		// TODO Ugly instanceof - polymorphism possible?
		else if (ingredient instanceof ComplexFood) {
			contains = ((ComplexFood) ingredient).containsIngredient(food);
		}
		else {
			contains = false;
		}

		return contains;
	}

	public boolean containsGroup(FoodGroup foodGroup) {
		return ingredient.isPartOfFoodGroup(foodGroup);
	}

	public boolean isSourceOfNutrient(Nutrient nutrient) {
		return ingredient.isSourceOfNutrient(nutrient);
	}

	public PreparationMethod getPreparationMethod() {
		return preparationMethod;
	}

	public void setPreparationMethod(PreparationMethod preparationMethod) {
		this.preparationMethod = preparationMethod;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PreparedFood)) {
			return false;
		}

		PreparedFood that = (PreparedFood) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getIngredient(),
				that.getIngredient()).append(this.getPreparationMethod(),
				that.getPreparationMethod());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getIngredient()).append(
				this.getPreparationMethod());
		return builder.toHashCode();
	}
}
