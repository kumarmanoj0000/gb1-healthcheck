package com.gb1.healthcheck.domain.foods;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.CollectionOfElements;

@Entity
@DiscriminatorValue("S")
public class SimpleFood extends Food {
	@Column(name = "FOOD_GROUP")
	private FoodGroup foodGroup = FoodGroup.OTHERS;

	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	public SimpleFood() {
	}

	public SimpleFood(SimpleFood source) {
		setName(source.getName());
		foodGroup = source.getFoodGroup();
		nutrients.addAll(source.getNutrients());
	}

	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	public SimpleFood setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
		return this;
	}

	@Override
	public boolean isPartOfFoodGroup(FoodGroup group) {
		return foodGroup.equals(group);
	}

	public SimpleFood addNutrient(Nutrient nutrient) {
		Validate.notNull(nutrient);
		nutrients.add(nutrient);
		return this;
	}

	public SimpleFood removeNutrient(Nutrient nutrient) {
		nutrients.remove(nutrient);
		return this;
	}

	public SimpleFood clearNutrients() {
		nutrients.clear();
		return this;
	}

	@Override
	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}

	@Override
	public boolean isSourceOfNutrient(Nutrient nutrient) {
		return nutrients.contains(nutrient);
	}
}
