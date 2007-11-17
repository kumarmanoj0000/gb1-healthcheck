package com.gb1.healthcheck.domain.metrics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.gb1.healthcheck.domain.users.User;

public class BodyMetrics {
	private User patient;
	private Map<Date, IntestinalState> intestinalStates = new HashMap<Date, IntestinalState>();

	public BodyMetrics(User patient) {
		Validate.notNull(patient);
		this.patient = patient;
	}

	public User getPatient() {
		return patient;
	}

	public IntestinalState getIntestinalState(Date instant) {
		return intestinalStates.get(instant);
	}

	public void setIntestinalState(Date instant, IntestinalState state) {
		intestinalStates.put(instant, state);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof BodyMetrics)) {
			return false;
		}

		BodyMetrics that = (BodyMetrics) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getPatient(), that.getPatient());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getPatient());
		return builder.toHashCode();
	}
}
