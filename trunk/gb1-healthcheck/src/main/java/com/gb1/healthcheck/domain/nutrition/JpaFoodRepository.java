package com.gb1.healthcheck.domain.nutrition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@SuppressWarnings("unchecked")
	public Set<SimpleFood> findSimpleFoods() {
		List<SimpleFood> foods = entityManager.createQuery("select sf from SimpleFood sf")
				.getResultList();
		Set<SimpleFood> foodSet = new HashSet<SimpleFood>(foods);

		return foodSet;
	}

	@SuppressWarnings("unchecked")
	public Set<ComplexFood> findComplexFoods() {
		List<ComplexFood> foods = entityManager.createQuery("select cf from ComplexFood cf")
				.getResultList();
		Set<ComplexFood> foodSet = new HashSet<ComplexFood>(foods);

		return foodSet;
	}

	public void saveSimpleFood(SimpleFood food) {
		entityManager.persist(food);
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
}
