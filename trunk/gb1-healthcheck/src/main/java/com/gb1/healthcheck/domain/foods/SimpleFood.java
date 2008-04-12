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
public class SimpleFood extends Food implements SimpleFoodCreationPropertyProvider {
	@Column(name = "FOOD_GROUP")
	private FoodGroup foodGroup;

	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	/**
	 * Package-protected constructor for JPA.
	 */
	SimpleFood() {
		super("");
	}

	SimpleFood(Long id, String name, FoodGroup foodGroup) {
		this(name, foodGroup);
		setId(id);
	}

	public SimpleFood(String name, FoodGroup foodGroup) {
		super(name);
		setFoodGroup(foodGroup);
	}

	public SimpleFood(SimpleFoodCreationPropertyProvider pp) {
		this(pp.getName(), pp.getFoodGroup());
		setNutrients(pp.getNutrients());
	}

	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	protected SimpleFood setFoodGroup(FoodGroup foodGroup) {
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

	@Override
	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}

	@Override
	public boolean isSourceOfNutrient(Nutrient nutrient) {
		return nutrients.contains(nutrient);
	}

	private SimpleFood setNutrients(Set<Nutrient> nutrients) {
		this.nutrients.clear();
		this.nutrients.addAll(nutrients);
		return this;
	}

	public void update(SimpleFoodUpdatePropertyProvider propertyProvider) {
		setName(propertyProvider.getName());
		setFoodGroup(propertyProvider.getFoodGroup());
		setNutrients(propertyProvider.getNutrients());
	}
}
