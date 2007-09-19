package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IntestinalStateMeasurement {
	private IntestinalState state;
	private Date instant;

	IntestinalStateMeasurement(IntestinalState state, Date instant) {
		Validate.notNull(instant);
		this.state = state;
		this.instant = instant;
	}

	public IntestinalState getState() {
		return state;
	}

	public Date getInstant() {
		return instant;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof IntestinalStateMeasurement)) {
			return false;
		}

		IntestinalStateMeasurement that = (IntestinalStateMeasurement) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getInstant(), that.getInstant());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getInstant());
		return builder.toHashCode();
	}
}
