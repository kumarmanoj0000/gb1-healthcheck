package com.gb1.healthcheck.domain.metrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKey;

import com.gb1.healthcheck.core.Identifiable;
import com.gb1.healthcheck.domain.users.User;

@Entity
public class PatientFile implements Identifiable {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private User patient;

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "GASTRIC_STATE")
	@MapKey(columns = @Column(name = "INSTANT"))
	@Column(name = "STATE")
	private Map<Date, GastricState> gastricStates = new HashMap<Date, GastricState>();

	PatientFile() {
	}

	public PatientFile(User patient) {
		Validate.notNull(patient);
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public User getPatient() {
		return patient;
	}

	public GastricState getGastricState(Date instant) {
		// all instants are truncated to the minute
		GastricState state = gastricStates.get(DateUtils.truncate(instant, Calendar.MINUTE));
		return state;
	}

	public void setGastricState(Date instant, GastricState state) {
		// all instants are truncated to the minute
		gastricStates.put(DateUtils.truncate(instant, Calendar.MINUTE), state);
	}

	public List<PunctualGastricState> getGastricStatesFor(Date day) {
		List<PunctualGastricState> states = new ArrayList<PunctualGastricState>();

		for (Date stateInstant : gastricStates.keySet()) {
			if (DateUtils.isSameDay(stateInstant, day)) {
				states.add(new PunctualGastricState(stateInstant, gastricStates.get(stateInstant)));
			}
		}

		Collections.sort(states, new PunctualGastricState.ByInstantComparator());

		return states;
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
