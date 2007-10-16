package com.gb1.healthcheck.domain.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@DiscriminatorValue("S")
public class SimpleFood extends Food implements SimpleFoodPropertyProvider {
	@Column(name = "FOOD_GROUP")
	private FoodGroup foodGroup;

	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	SimpleFood() {
		super("");
	}

	public SimpleFood(String name, FoodGroup foodGroup) {
		super(name);
		setFoodGroup(foodGroup);
	}

	public SimpleFood(SimpleFoodPropertyProvider propertyProvider) {
		this(propertyProvider.getName(), propertyProvider.getFoodGroup());
		nutrients.addAll(propertyProvider.getNutrients());
	}

	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	protected SimpleFood setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
		return this;
	}

	public boolean isPartOfFoodGroup(FoodGroup group) {
		return foodGroup.equals(group);
	}

	protected SimpleFood addNutrient(Nutrient nutrient) {
		nutrients.add(nutrient);
		return this;
	}

	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}

	public boolean isSourceOfNutrient(Nutrient nutrient) {
		return nutrients.contains(nutrient);
	}

	protected SimpleFood setNutrients(Set<Nutrient> nutrients) {
		this.nutrients.clear();
		this.nutrients.addAll(nutrients);
		return this;
	}

	public void update(SimpleFoodMutablePropertyProvider propertyProvider) {
		setName(propertyProvider.getName());
		setFoodGroup(propertyProvider.getFoodGroup());
		setNutrients(propertyProvider.getNutrients());
	}
}
