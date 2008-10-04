package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gb1.healthcheck.domain.users.User;

@Repository("mealRepository")
public class JpaMealRepository implements MealRepository {
	private EntityManager entityManager = null;

	public JpaMealRepository() {
	}

	public Meal loadMeal(Long mealId) {
		return entityManager.find(Meal.class, mealId);
	}

	@SuppressWarnings("unchecked")
	public List<Meal> findMealsBy(User eater) {
		return entityManager.createQuery("select m from Meal m where m.eater = ?1").setParameter(1,
				eater).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Meal> findMealsBy(User eater, Date instant) {
		return entityManager.createQuery(
				"select m from Meal m where m.eater = ?1 and m.instant = ?2")
				.setParameter(1, eater).setParameter(2, instant).getResultList();
	}

	public void persistMeal(Meal meal) {
		entityManager.persist(meal);
	}

	public void mergeMeal(Meal meal) {
		entityManager.merge(meal);
	}

	public void deleteMeal(Meal meal) {
		entityManager.remove(meal);
	}

	public Meal getLastMealBy(User eater) {
		Meal lastMeal = null;

		try {
			lastMeal = (Meal) entityManager.createQuery(
					"select m from Meal m where m.eater = ?1 order by m.instant desc")
					.setParameter(1, eater).setMaxResults(1).getSingleResult();
		}
		catch (NoResultException e) {
			// user never ate
		}

		return lastMeal;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
