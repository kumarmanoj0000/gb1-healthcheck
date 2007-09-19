package com.gb1.healthcheck.domain.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SimpleFood implements Food {
	private String name;
	private Group group;
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	public SimpleFood(String name, Group group) {
		Validate.notNull(name);
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
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

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SimpleFood)) {
			return false;
		}

		SimpleFood that = (SimpleFood) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getName(), that.getName());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getName());
		return builder.toHashCode();
	}
}
