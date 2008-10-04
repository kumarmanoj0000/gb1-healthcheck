package com.gb1.healthcheck.domain.foods;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository("foodRepository")
public class JpaFoodRepository implements FoodRepository {
	@PersistenceContext
	private EntityManager entityManager = null;

	public JpaFoodRepository() {
	}

	public void persist(Food food) {
		entityManager.persist(food);
	}

	public void merge(Food food) {
		entityManager.merge(food);
	}

	public Food loadFood(Long foodId) {
		return entityManager.find(Food.class, foodId);
	}

	public SimpleFood loadSimpleFood(Long foodId) {
		return entityManager.find(SimpleFood.class, foodId);
	}

	public ComplexFood loadComplexFood(Long foodId) {
		return entityManager.find(ComplexFood.class, foodId);
	}

	public Food findFoodByName(String name) {
		List<Food> foods = findFoodsByName(name);

		Food food = null;
		if (foods.size() == 1) {
			food = foods.get(0);
		}

		return food;
	}

	@SuppressWarnings("unchecked")
	public List<Food> findFoodsByName(String name) {
		return entityManager.createQuery("select f from Food f where f.name = ?1").setParameter(1,
				name).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SimpleFood> findSimpleFoods() {
		return entityManager.createQuery("select sf from SimpleFood sf").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ComplexFood> findComplexFoods() {
		return entityManager.createQuery("select cf from ComplexFood cf").getResultList();
	}

	public void delete(Food food) {
		entityManager.remove(food);
	}
}
