package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gb1.healthcheck.domain.users.User;

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

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
