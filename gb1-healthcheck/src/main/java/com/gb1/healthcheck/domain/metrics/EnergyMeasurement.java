package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EnergyMeasurement {
	private EnergyLevel level;
	private Date instant;

	EnergyMeasurement(EnergyLevel level, Date instant) {
		Validate.notNull(instant);
		this.level = level;
		this.instant = instant;
	}

	public EnergyLevel getLevel() {
		return level;
	}

	public Date getInstant() {
		return instant;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EnergyMeasurement)) {
			return false;
		}

		EnergyMeasurement that = (EnergyMeasurement) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getInstant(), that.getInstant());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getInstant());
		return builder.toHashCode();
	}
}
