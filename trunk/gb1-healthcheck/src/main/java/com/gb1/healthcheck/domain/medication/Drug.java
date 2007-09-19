package com.gb1.healthcheck.domain.medication;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Drug {
	private String name;

	public Drug(String name) {
		Validate.notNull(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Drug)) {
			return false;
		}

		Drug that = (Drug) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getName(), that.getName());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getName());
		return builder.toHashCode();
	}
}
