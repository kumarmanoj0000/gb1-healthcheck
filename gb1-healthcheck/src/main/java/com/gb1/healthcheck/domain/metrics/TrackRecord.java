package com.gb1.healthcheck.domain.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.users.User;

public class TrackRecord {
	private User patient;
	private List<Meal> meals = new ArrayList<Meal>();
	private List<EnergyMeasurement> energyMeasurements = new ArrayList<EnergyMeasurement>();
	private List<IntestinalStateMeasurement> intestinalStateMeasurements = new ArrayList<IntestinalStateMeasurement>();

	public TrackRecord(User patient) {
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

	public void addEnergyMeasurement(EnergyLevel level, Date instant) {
		energyMeasurements.add(new EnergyMeasurement(level, instant));
	}

	public void removeEnergyMeasurement(EnergyMeasurement em) {
		energyMeasurements.remove(em);
	}

	public List<EnergyMeasurement> getEnergyMeasurements() {
		return Collections.unmodifiableList(energyMeasurements);
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
		if (!(o instanceof TrackRecord)) {
			return false;
		}

		TrackRecord that = (TrackRecord) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getPatient(), that.getPatient());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getPatient());
		return builder.toHashCode();
	}
}
