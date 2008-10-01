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

	public User loadUser(Long userId) {
		return entityManager.find(User.class, userId);
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsers() {
		return entityManager.createQuery("select u from User u").getResultList();
	}

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

	public User findUserByEmail(String email) {
		List<User> users = findUsersByEmail(email);

		User user = null;
		if (users.size() == 1) {
			user = users.get(0);
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsersByEmail(String email) {
		return entityManager.createQuery("select u from User as u where u.email = ?1")
				.setParameter(1, email).getResultList();
	}

	public void persistUser(User user) {
		entityManager.persist(user);
	}

	public void mergeUser(User user) {
		entityManager.merge(user);
	}

	public void deleteUser(User user) {
		entityManager.remove(user);
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
