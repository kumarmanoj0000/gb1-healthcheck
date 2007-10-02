package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodValidator;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodValidator;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;
	private SimpleFoodValidator simpleFoodCreationValidator;
	private SimpleFoodValidator simpleFoodUpdateValidator;
	private ComplexFoodValidator complexFoodCreationValidator;

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
	public void createComplexFood(ComplexFoodPropertyProvider propertyProvider)
			throws FoodException {
		ComplexFood food = new ComplexFood(propertyProvider);
		complexFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
	}

	@Transactional(rollbackFor = { RuntimeException.class, FoodException.class })
	public void updateSimpleFood(Long foodId, SimpleFoodMutablePropertyProvider propertyProvider)
			throws FoodException {
		SimpleFood food = foodRepo.loadSimpleFood(foodId);
		food.update(propertyProvider);
		simpleFoodUpdateValidator.validate(food);
	}

	@Transactional(rollbackFor = { RuntimeException.class })
	public void deleteSimpleFood(Long foodId) {
		foodRepo.deleteFood(foodId);
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}

	public void setSimpleFoodCreationValidator(SimpleFoodValidator validator) {
		this.simpleFoodCreationValidator = validator;
	}

	public void setSimpleFoodUpdateValidator(SimpleFoodValidator validator) {
		this.simpleFoodUpdateValidator = validator;
	}

	public void setComplexFoodCreationValidator(ComplexFoodValidator validator) {
		this.complexFoodCreationValidator = validator;
	}
}
