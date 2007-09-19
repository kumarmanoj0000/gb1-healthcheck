package com.gb1.healthcheck.domain.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ComplexFood implements Food {
	private String name;
	private Set<Food> ingredients = new HashSet<Food>();

	public ComplexFood(String name) {
		Validate.notNull(name);
		this.name = name;
	}

	public String getName() {
		return name;
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
			else if (candidate instanceof ComplexFood) {
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
			if (ingredient.getNutrients().contains(nutrient)) {
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
		if (!(o instanceof ComplexFood)) {
			return false;
		}

		ComplexFood that = (ComplexFood) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getName(), that.getName());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getName());
		return builder.toHashCode();
	}
}
