package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodCreationValidator;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;
	private SimpleFoodCreationValidator simpleFoodCreationValidator = null;

	public FoodServiceImpl() {
	}

	@Transactional(readOnly = true)
	public SimpleFood loadSimpleFood(Long foodId) {
		return foodRepo.loadSimpleFood(foodId);
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
	public void createSimpleFood(SimpleFoodPropertyProvider propertyProvider) throws FoodException {
		SimpleFood food = new SimpleFood(propertyProvider);
		simpleFoodCreationValidator.validate(food);
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

	public void setSimpleFoodCreationValidator(SimpleFoodCreationValidator validator) {
		this.simpleFoodCreationValidator = validator;
	}
}
