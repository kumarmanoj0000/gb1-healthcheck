package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaMealRepository implements MealRepository {
	private EntityManager entityManager = null;

	public JpaMealRepository() {
	}

	public Meal loadMeal(Long mealId) {
		Meal meal = entityManager.find(Meal.class, mealId);
		return meal;
	}

	@SuppressWarnings("unchecked")
	public List<Meal> loadMeals() {
		List<Meal> meals = entityManager.createQuery("select m from Meal m").getResultList();
		return meals;
	}

	@SuppressWarnings("unchecked")
	public List<Meal> findMealsByInstant(Date instant) {
		List<Meal> meals = entityManager.createQuery("select m from Meal m where m.instant = ?1")
				.setParameter(1, instant).getResultList();
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
