package com.gb1.healthcheck.domain.nutrition;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaFoodRepository implements FoodRepository {
	private EntityManager entityManager = null;

	public JpaFoodRepository() {
	}

	public void saveFood(Food food) {
		entityManager.persist(food);
	}

	public Food loadFood(Long foodId) {
		return entityManager.find(Food.class, foodId);
	}

	@SuppressWarnings("unchecked")
	public Food findFoodByName(String name) {
		List<Food> foods = entityManager.createQuery("select f from Food f where f.name = ?1")
				.setParameter(1, name).getResultList();

		Food food = null;
		if (foods.size() == 1) {
			food = foods.get(0);
		}

		return food;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
