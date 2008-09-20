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
		Meal meal = entityManager.find(Meal.class, mealId);
		return meal;
	}

	@SuppressWarnings("unchecked")
	public List<Meal> findMealsBy(User eater) {
		List<Meal> meals = entityManager.createQuery("select m from Meal m where m.eater = ?1")
				.setParameter(1, eater).getResultList();
		return meals;
	}

	@SuppressWarnings("unchecked")
	public List<Meal> findMealsBy(User eater, Date instant) {
		List<Meal> meals = entityManager.createQuery(
				"select m from Meal m where m.eater = ?1 and m.instant = ?2")
				.setParameter(1, eater).setParameter(2, instant).getResultList();
		return meals;
	}

	public void saveMeal(Meal meal) {
		entityManager.persist(meal);
	}

	public void deleteMeal(Long mealId) {
		entityManager.remove(loadMeal(mealId));
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
