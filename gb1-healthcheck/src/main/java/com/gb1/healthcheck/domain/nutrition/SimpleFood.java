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
	private Group group;

	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	SimpleFood() {
		super("");
	}

	public SimpleFood(String name, Group group) {
		super(name);
		this.group = group;
	}

	public SimpleFood(SimpleFoodPropertyProvider propertyProvider) {
		this(propertyProvider.getName(), propertyProvider.getGroup());
		nutrients.addAll(propertyProvider.getNutrients());
	}

	public Group getGroup() {
		return group;
	}

	public boolean isPartOfGroup(Group someGroup) {
		return group.equals(someGroup);
	}

	public SimpleFood addNutrient(Nutrient nutrient) {
		nutrients.add(nutrient);
		return this;
	}

	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}

	public boolean isSourceOfNutrient(Nutrient nutrient) {
		return nutrients.contains(nutrient);
	}

	public void update(SimpleFoodMutablePropertyProvider propertyProvider) {
		setName(propertyProvider.getName());
		group = propertyProvider.getGroup();

		nutrients.clear();
		nutrients.addAll(propertyProvider.getNutrients());
	}
}
