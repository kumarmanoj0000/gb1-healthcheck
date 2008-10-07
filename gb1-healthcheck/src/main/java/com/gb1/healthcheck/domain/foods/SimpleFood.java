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

	public SimpleFood(SimpleFood food) {
		super(food);

		foodGroup = food.getFoodGroup();
		nutrients.addAll(food.getNutrients());
	}

	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	public void setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
	}

	@Override
	public boolean isPartOfFoodGroup(FoodGroup group) {
		return foodGroup.equals(group);
	}

	public void addNutrient(Nutrient nutrient) {
		Validate.notNull(nutrient);
		nutrients.add(nutrient);
	}

	public void removeNutrient(Nutrient nutrient) {
		nutrients.remove(nutrient);
	}

	public void clearNutrients() {
		nutrients.clear();
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
