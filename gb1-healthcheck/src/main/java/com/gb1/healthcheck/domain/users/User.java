package com.gb1.healthcheck.domain.users;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.CollectionOfElements;

import com.gb1.commons.Identifiable;
import com.gb1.commons.tokens.Token;

/**
 * A user of the current system. A user is identified by the usual properties such as a login name
 * and an email address. <p/> When initially created, a user is considered inactive. His activation
 * must first be requested, at which point the user will be informed and provided an activation
 * token. This token must then be confirmed in order to activate his account. <p/> A user can have
 * zero or many roles that will give him access to priviledged actions.
 * 
 * @author Guillaume Bilodeau
 */
@Entity
public class User implements Identifiable, UserPropertyProvider, Serializable {
	/**
	 * The user's identifier
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The user's login name
	 */
	private String login;

	/**
	 * The user's email address
	 */
	private String email;

	/**
	 * The user's password
	 */
	private String password;

	/**
	 * The user's status
	 */
	private UserStatus status = UserStatus.CREATED;

	/**
	 * The user's activation token. Provided when activation is requested.
	 */
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "activationToken"))
	private Token activationToken = null;

	/**
	 * The user's roles
	 */
	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * Creates a new user.
	 */
	User() {
	}

	/**
	 * Creates a new user based on the properties provided by the provider. <p/> Since
	 * <code>User</code> implements the <UserCreationPropertyProvider> interface, this constructor
	 * can act as a typical copy constructor.
	 * 
	 * @param propertyProvider The user property provider
	 */
	public User(UserPropertyProvider propertyProvider) {
		login = propertyProvider.getLogin();
		email = propertyProvider.getEmail();
		password = propertyProvider.getPassword();

		for (Role role : propertyProvider.getRoles()) {
			roles.add(role);
		}
	}

	/**
	 * Returns the user's ID.
	 * 
	 * @return The user's ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the user's ID.
	 * 
	 * @param id The user's new ID
	 */
	void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the user's login name.
	 * 
	 * @return The user's login name
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the user's new login name.
	 * 
	 * @param login The user's new login name
	 */
	void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the user's password.
	 * 
	 * @return The user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's new password. Normally the password should be changed through the
	 * <code>changePassword(String, String)</code> method that provides additional security
	 * checks.
	 * 
	 * @param password The user's new password
	 */
	void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Changes the user's password. His current password must be provided to check the validity of
	 * the request. If the caller cannot provide the user's current password, a
	 * <code>WrongPasswordException</code> is thrown.
	 * 
	 * @param currentPassword The user's current password
	 * @param newPassword The user's new password
	 * @throws InvalidPasswordException When the provided current password is wrong
	 */
	public void changePassword(String currentPassword, String newPassword)
			throws InvalidPasswordException {
		if (!password.equals(currentPassword)) {
			throw new InvalidPasswordException(currentPassword);
		}

		password = newPassword;
	}

	/**
	 * Returns the user's email address.
	 * 
	 * @return The user's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's new email address.
	 * 
	 * @param email The user's new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the user's activation token. If activation has not yet been requested, will return
	 * null.
	 * 
	 * @return The activation token
	 */
	Token getActivationToken() {
		return activationToken;
	}

	/**
	 * Notifies the user that his account is pending activation. The request he is given contains
	 * the token that will need to be confirmed in order to activate his account.
	 * 
	 * @param request The activation request, containing the activation token
	 */
	void activationRequested(UserActivationRequest request) {
		activationToken = request.getActivationToken();
		status = UserStatus.PENDING_ACTIVATION;
	}

	/**
	 * Activates the current user. For activation to be successfully completed, the user must be
	 * currently inactive, activation must have been requested and the provided token must be equal
	 * to the one assigned when activation was requested.
	 * 
	 * @param candidateToken The candidate activation token
	 * @throws UserAlreadyActiveException When the user is already active
	 * @throws UserActivationNotRequestedException When activation has not yet been requested for
	 *         this user
	 * @throws InvalidTokenException When the provided token is not equal to the one assigned when
	 *         activation was requested
	 */
	public void activate(Token candidateToken) throws UserActivationException {
		if (isActive()) {
			throw new UserAlreadyActiveException();
		}

		if (!isPendingActivation()) {
			throw new UserActivationNotRequestedException();
		}

		if (candidateToken == null || !activationToken.equals(candidateToken)) {
			throw new InvalidTokenException();
		}

		status = UserStatus.ACTIVE;
	}

	/**
	 * Verifies whether the current user is waiting for a request for activation or not.
	 * 
	 * @return true if the user is waiting for a request for activation; false otherwise
	 */
	public boolean isWaitingForActivationRequest() {
		return (status == UserStatus.CREATED);
	}

	/**
	 * Verifies whether the current user is pending activation or not.
	 * 
	 * @return true if the user is pending activation; false otherwise
	 */
	public boolean isPendingActivation() {
		return (status == UserStatus.PENDING_ACTIVATION);
	}

	/**
	 * Verifies whether the current user is active or not.
	 * 
	 * @return true if the user is active; false otherwise
	 */
	public boolean isActive() {
		return (status == UserStatus.ACTIVE);
	}

	/**
	 * Assigns a new role to the current user. If the given role has already been assigned, the call
	 * has no effect.
	 * 
	 * @param role The new role to assign
	 */
	public void assignRole(Role role) {
		Validate.notNull(role);
		roles.add(role);
	}

	/**
	 * Relieves the current user from a role. If the given role has not been already assigned to the
	 * user, the call has no effect.
	 * 
	 * @param role The role to remove
	 */
	public void relieveFromRole(Role role) {
		Validate.notNull(role);
		roles.remove(role);
	}

	/**
	 * Returns the user's assigned roles.
	 * 
	 * @return The user's assigned roles
	 */
	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	/**
	 * Verifies whether the current user has been assigned the given role.
	 * 
	 * @param role The role to check
	 * @return true is the user has been assigned the given role; false otherwise
	 */
	public boolean hasRole(Role role) {
		Validate.notNull(role);
		return roles.contains(role);
	}

	/**
	 * Updates the current user based on the given property provider.
	 * 
	 * @param propertyProvider The update property provider
	 */
	public void update(UserUpdatePropertyProvider propertyProvider) {
		email = propertyProvider.getEmail();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		User that = (User) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.getLogin(), that.getLogin());

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.getLogin());
		return builder.toHashCode();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this).append(id).append(login).append(email)
				.append(password).append(status).append(activationToken).append(roles);
		return builder.toString();
	}

	public static class ByLoginComparator implements Comparator<User> {
		public int compare(User u1, User u2) {
			CompareToBuilder builder = new CompareToBuilder().append(u1.getLogin(), u2.getLogin());
			return builder.toComparison();
		}
	}

	public static class ByEmailComparator implements Comparator<User> {
		public int compare(User u1, User u2) {
			CompareToBuilder builder = new CompareToBuilder().append(u1.getEmail(), u2.getEmail());
			return builder.toComparison();
		}
	}
}
