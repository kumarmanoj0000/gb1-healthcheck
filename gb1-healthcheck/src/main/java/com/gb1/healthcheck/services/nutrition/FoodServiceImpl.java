package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;

public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;

	public FoodServiceImpl() {
	}

	public Set<SimpleFood> getSimpleFoods() {
		return foodRepo.findSimpleFoods();
	}

	public Set<ComplexFood> getComplexFoods() {
		return foodRepo.findComplexFoods();
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}
