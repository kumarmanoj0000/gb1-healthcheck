package com.gb1.healthcheck.domain.metrics;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PunctualGastricState {
	private Date instant;
	private GastricState state;

	public PunctualGastricState(Date instant, GastricState state) {
		this.instant = instant;
		this.state = state;
	}

	public Date getInstant() {
		return new Date(instant.getTime());
	}

	public GastricState getState() {
		return state;
	}

	public static class ByInstantComparator implements Comparator<PunctualGastricState> {
		public int compare(PunctualGastricState state1, PunctualGastricState state2) {
			return state1.getInstant().compareTo(state2.getInstant());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PunctualGastricState)) {
			return false;
		}

		PunctualGastricState that = (PunctualGastricState) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getInstant(), that.getInstant());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getInstant());
		return builder.toHashCode();
	}
}
