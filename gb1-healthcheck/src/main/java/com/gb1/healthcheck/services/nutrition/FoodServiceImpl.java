package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;

	public FoodServiceImpl() {
	}

	@Transactional(readOnly = true)
	public Set<SimpleFood> getSimpleFoods() {
		return foodRepo.findSimpleFoods();
	}

	@Transactional(readOnly = true)
	public Set<ComplexFood> getComplexFoods() {
		return foodRepo.findComplexFoods();
	}

	@Transactional(rollbackFor = { RuntimeException.class, FoodException.class })
	public void createSimpleFood(SimpleFoodPropertyProvider propertyProvider) {
		SimpleFood food = new SimpleFood(propertyProvider);
		foodRepo.saveSimpleFood(food);
	}

	@Transactional(rollbackFor = { RuntimeException.class, FoodException.class })
	public void updateSimpleFood(Long foodId, SimpleFoodPropertyProvider propertyProvider)
			throws FoodException {
		SimpleFood food = foodRepo.loadSimpleFood(foodId);
		food.update(propertyProvider);
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
