package com.gb1.healthcheck.domain.foods;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.Validate;

@Entity
@DiscriminatorValue("C")
public class ComplexFood extends Food implements ComplexFoodCreationPropertyProvider {
	@ManyToMany
	@JoinTable(name = "FOOD_INGREDIENTS")
	private Set<Food> ingredients = new HashSet<Food>();

	/**
	 * Package-protected constructor for JPA.
	 */
	ComplexFood() {
		this(null, "");
	}

	public ComplexFood(String name) {
		this(null, name);
	}

	protected ComplexFood(Long id, String name) {
		super(name);
		setId(id);
	}

	public ComplexFood(ComplexFoodCreationPropertyProvider propertyProvider) {
		super(propertyProvider.getName());
		setIngredients(propertyProvider.getIngredients());
	}

	public ComplexFood addIngredient(Food ingredient) {
		Validate.notNull(ingredient);
		ingredients.add(ingredient);

		return this;
	}

	public boolean containsIngredient(Food ingredient) {
		for (Food candidate : ingredients) {
			if (candidate.equals(ingredient)) {
				return true;
			}
			// TODO Ugly instanceof - polymorphism possible?
			else if (candidate instanceof ComplexFood) {
				ComplexFood complexCandidate = (ComplexFood) candidate;
				if (complexCandidate.containsIngredient(ingredient)) {
					return true;
				}
			}
		}

		return false;
	}

	public Set<Food> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}

	private void setIngredients(Set<Food> ingredients) {
		this.ingredients.clear();
		this.ingredients.addAll(ingredients);
	}

	@Override
	public boolean isPartOfFoodGroup(FoodGroup foodGroup) {
		for (Food ingredient : ingredients) {
			if (ingredient.isPartOfFoodGroup(foodGroup)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Nutrient> getNutrients() {
		Set<Nutrient> allNutrients = new HashSet<Nutrient>();
		for (Food ingredient : ingredients) {
			allNutrients.addAll(ingredient.getNutrients());
		}

		return Collections.unmodifiableSet(allNutrients);
	}

	@Override
	public boolean isSourceOfNutrient(Nutrient nutrient) {
		for (Food ingredient : ingredients) {
			if (ingredient.isSourceOfNutrient(nutrient)) {
				return true;
			}
		}

		return false;
	}

	public void update(ComplexFoodUpdatePropertyProvider propertyProvider) {
		setName(propertyProvider.getName());
		setIngredients(propertyProvider.getIngredients());
	}
}
