package com.gb1.healthcheck.domain.medication;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DrugIntake {
	private static final int DEFAULT_QUANTITY = 1;

	private Drug drug;
	private int quantity;
	private Date instant;

	public DrugIntake(Drug drug, Date instant) {
		this(drug, DEFAULT_QUANTITY, instant);
	}

	public DrugIntake(Drug drug, int quantity, Date instant) {
		Validate.notNull(drug);
		Validate.isTrue(quantity > 0);
		Validate.notNull(instant);

		this.drug = drug;
		this.quantity = quantity;
		this.instant = instant;
	}

	public Drug getDrug() {
		return drug;
	}

	public int getQuantity() {
		return quantity;
	}

	public Date getInstant() {
		return instant;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DrugIntake)) {
			return false;
		}

		// one could take two differents drugs at the same time
		DrugIntake that = (DrugIntake) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getDrug(), that.getDrug()).append(
				this.getInstant(), that.getInstant());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getDrug()).append(
				this.getInstant());
		return builder.toHashCode();
	}
}
