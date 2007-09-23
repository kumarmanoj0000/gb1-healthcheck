package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

public class SimpleFoodCreationRequest implements SimpleFoodPropertyProvider {
	private String name;
	private Group group;
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	public SimpleFoodCreationRequest() {
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSelectedNutrients(Nutrient[] selectedNutrients) {
		nutrients.clear();
		for (Nutrient n : selectedNutrients) {
			nutrients.add(n);
		}
	}

	public Nutrient[] getSelectedNutrients() {
		return nutrients.toArray(new Nutrient[0]);
	}

	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}
}
