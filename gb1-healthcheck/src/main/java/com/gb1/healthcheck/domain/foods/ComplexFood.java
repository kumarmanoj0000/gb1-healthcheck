package com.gb1.healthcheck.domain.foods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.Validate;

@Entity
@DiscriminatorValue("C")
public class ComplexFood extends Food {
	@ManyToMany
	@JoinTable(name = "FOOD_INGREDIENTS")
	private List<Food> ingredients = new ArrayList<Food>();

	public ComplexFood() {
	}

	public ComplexFood(ComplexFood food) {
		super(food);
		ingredients.addAll(food.getIngredients());
	}

	public void addIngredient(Food ingredient) {
		Validate.notNull(ingredient);
		ingredients.add(ingredient);
	}

	public void addIngredients(Collection<Food> ingredients) {
		for (Food ingredient : ingredients) {
			addIngredient(ingredient);
		}
	}

	public boolean containsIngredient(Food ingredient) {
		for (Food candidate : ingredients) {
			if (candidate.equals(ingredient)) {
				return true;
			}
			else if (candidate instanceof ComplexFood) {
				ComplexFood complexCandidate = (ComplexFood) candidate;
				if (complexCandidate.containsIngredient(ingredient)) {
					return true;
				}
			}
		}

		return false;
	}

	public void clearIngredients() {
		ingredients.clear();
	}

	public List<Food> getIngredients() {
		return Collections.unmodifiableList(ingredients);
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
}
