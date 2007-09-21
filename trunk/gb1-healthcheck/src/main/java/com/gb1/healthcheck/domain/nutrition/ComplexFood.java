package com.gb1.healthcheck.domain.nutrition;

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
public class ComplexFood extends Food {
	@ManyToMany
	@JoinTable(name = "FOOD_INGREDIENTS")
	private Set<Food> ingredients = new HashSet<Food>();

	ComplexFood() {
		super("");
	}

	public ComplexFood(String name) {
		super(name);
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
			} else if (candidate instanceof ComplexFood) {
				ComplexFood complexCandidate = (ComplexFood) candidate;
				if (complexCandidate.containsIngredient(ingredient)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isPartOfGroup(Group group) {
		for (Food ingredient : ingredients) {
			if (ingredient.isPartOfGroup(group)) {
				return true;
			}
		}
		return false;
	}

	public Set<Nutrient> getNutrients() {
		Set<Nutrient> allNutrients = new HashSet<Nutrient>();
		for (Food ingredient : ingredients) {
			allNutrients.addAll(ingredient.getNutrients());
		}

		return Collections.unmodifiableSet(allNutrients);
	}

	public boolean isSourceOfNutrient(Nutrient nutrient) {
		for (Food ingredient : ingredients) {
			if (ingredient.isSourceOfNutrient(nutrient)) {
				return true;
			}
		}

		return false;
	}
}
