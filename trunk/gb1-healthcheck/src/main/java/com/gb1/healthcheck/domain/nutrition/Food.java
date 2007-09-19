package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface Food {
	String getName();

	boolean isPartOfGroup(Group group);

	Set<Nutrient> getNutrients();

	boolean isSourceOfNutrient(Nutrient nutrient);
}
