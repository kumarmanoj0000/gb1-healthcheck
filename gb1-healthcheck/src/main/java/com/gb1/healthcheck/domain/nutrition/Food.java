package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

import com.gb1.commons.Identifiable;

public interface Food extends Identifiable {
	String getName();

	boolean isPartOfGroup(Group group);

	Set<Nutrient> getNutrients();

	boolean isSourceOfNutrient(Nutrient nutrient);
}
