package com.gb1.healthcheck.domain.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * A JPA-based repository for users.
 * 
 * @author Guillaume Bilodeau
 */
@Repository("userRepository")
public class JpaUserRepository implements UserRepository {
	private EntityManager entityManager = null;

	public JpaUserRepository() {
	}

	/**
	 * Loads the user identified by the given ID. If no user corresponds to this ID, null is
	 * returned.
	 * 
	 * @param userId The ID of the user to load
	 * @return The corresponding user; null if not found
	 */
	public User loadUser(Long userId) {
		User user = entityManager.find(User.class, userId);
		return user;
	}

	/**
	 * Finds all registered users.
	 * 
	 * @return The list of registered users
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUsers() {
		List<User> users = entityManager.createQuery("select u from User u").getResultList();
		return users;
	}

	/**
	 * Finds a user identified by the given login name. If no user corresponds to this login name,
	 * null is returned.
	 * 
	 * @param login The login name of the user to find
	 * @return The corresponding user; null if not found
	 */
	@SuppressWarnings("unchecked")
	public User findUserByLogin(String login) {
		List<User> users = entityManager.createQuery("select u from User u where u.login = ?1")
				.setParameter(1, login).getResultList();

		User user = null;
		if (users.size() == 1) {
			user = users.get(0);
		}

		return user;
	}

	/**
	 * Finds a user identified by the given email address. If no user corresponds to this email
	 * address, null is returned.
	 * 
	 * @param email The email address of the user to find
	 * @return The corresponding user; null if not found
	 */
	public User findUserByEmail(String email) {
		List<User> users = findUsersByEmail(email);

		User user = null;
		if (users.size() == 1) {
			user = users.get(0);
		}

		return user;
	}

	/**
	 * Finds all users who tentatively own the given email address. This is possible when a user has
	 * just been updated with an already owned email address and the transaction has not yet been
	 * committed.
	 * 
	 * @param email The email address of the users to find
	 * @return The corresponding users; empty if not found
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUsersByEmail(String email) {
		List<User> users = entityManager.createQuery("select u from User as u where u.email = ?1")
				.setParameter(1, email).getResultList();
		return users;
	}

	/**
	 * Saves the given user.
	 * 
	 * @param user The user to save
	 */
	public void saveUser(User user) {
		entityManager.persist(user);
	}

	public void deleteUser(Long userId) {
		entityManager.remove(loadUser(userId));
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
