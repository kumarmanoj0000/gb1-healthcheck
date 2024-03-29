package com.gb1.healthcheck.domain.foods;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.healthcheck.core.Identifiable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class Food implements Identifiable, Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	protected Food() {
	}

	protected Food(Food food) {
		this.name = food.getName();
	}

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract boolean isPartOfFoodGroup(FoodGroup foodGroup);

	public abstract Set<Nutrient> getNutrients();

	public abstract boolean isSourceOfNutrient(Nutrient nutrient);

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Food)) {
			return false;
		}

		Food that = (Food) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getName(), that.getName());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getName());
		return builder.toHashCode();
	}

	public static class ByNameComparator implements Comparator<Food> {
		public int compare(Food f1, Food f2) {
			CompareToBuilder builder = new CompareToBuilder().append(f1.getName(), f2.getName());
			return builder.toComparison();
		}
	}
}
