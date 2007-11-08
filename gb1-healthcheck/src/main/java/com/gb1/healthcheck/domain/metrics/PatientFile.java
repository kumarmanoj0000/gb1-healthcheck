package com.gb1.healthcheck.domain.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.users.User;

public class PatientFile {
	private User patient;
	private List<Meal> meals = new ArrayList<Meal>();
	private List<IntestinalStateMeasurement> intestinalStateMeasurements = new ArrayList<IntestinalStateMeasurement>();

	public PatientFile(User patient) {
		Validate.notNull(patient);
		this.patient = patient;
	}

	public User getPatient() {
		return patient;
	}

	public void addMeal(Meal meal) {
		Validate.notNull(meal);
		meals.add(meal);
	}

	public void removeMeal(Meal meal) {
		meals.remove(meal);
	}

	public List<Meal> getMeals() {
		return Collections.unmodifiableList(meals);
	}

	public void addIntestinalStateMeasurement(IntestinalState state, Date instant) {
		intestinalStateMeasurements.add(new IntestinalStateMeasurement(state, instant));
	}

	public void removeIntestinalStateMeasurement(IntestinalStateMeasurement ism) {
		intestinalStateMeasurements.remove(ism);
	}

	public List<IntestinalStateMeasurement> getIntestinalStateMeasurements() {
		return Collections.unmodifiableList(intestinalStateMeasurements);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PatientFile)) {
			return false;
		}

		PatientFile that = (PatientFile) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getPatient(), that.getPatient());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getPatient());
		return builder.toHashCode();
	}
}
