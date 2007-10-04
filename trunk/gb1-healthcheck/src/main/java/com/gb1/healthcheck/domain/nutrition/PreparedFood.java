package com.gb1.healthcheck.domain.nutrition;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PreparedFood {
	private Food ingredient;
	private PreparationMethod preparationMethod;

	public PreparedFood(Food ingredient, PreparationMethod preparationMethod) {
		Validate.notNull(ingredient);

		this.ingredient = ingredient;
		this.preparationMethod = preparationMethod;
	}

	public String getName() {
		return ingredient.getName();
	}

	public Food getIngredient() {
		return ingredient;
	}

	public boolean containsIngredient(Food food) {
		boolean contains;

		if (ingredient instanceof ComplexFood) {
			contains = ((ComplexFood) ingredient).containsIngredient(food);
		}
		else {
			contains = ingredient.equals(food);
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
