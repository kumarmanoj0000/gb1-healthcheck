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

	public void setSelectedNutrients(String[] nutrientNames) {
		nutrients.clear();
		for (String nutrientName : nutrientNames) {
			nutrients.add(Nutrient.valueOf(nutrientName));
		}
	}

	public String[] getSelectedNutrients() {
		String[] nutrientNames = new String[nutrients.size()];
		int i = 0;

		for (Nutrient n : nutrients) {
			nutrientNames[i++] = n.name();
		}

		return nutrientNames;
	}

	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}
}
