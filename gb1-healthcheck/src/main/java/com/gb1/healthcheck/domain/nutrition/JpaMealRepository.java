package com.gb1.healthcheck.domain.nutrition;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaMealRepository implements MealRepository {
	private EntityManager entityManager = null;

	public JpaMealRepository() {
	}

	@SuppressWarnings("unchecked")
	public List<Meal> loadMeals() {
		List<Meal> meals = entityManager.createQuery("select m from Meal m").getResultList();
		return meals;
	}

	public void saveMeal(Meal meal) {
		entityManager.persist(meal);
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
