package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodValidator;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodValidator;

public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;
	private SimpleFoodValidator simpleFoodCreationValidator;
	private SimpleFoodValidator simpleFoodUpdateValidator;
	private ComplexFoodValidator complexFoodCreationValidator;
	private ComplexFoodValidator complexFoodUpdateValidator;

	public FoodServiceImpl() {
	}

	@Transactional(readOnly = true)
	public SimpleFood loadSimpleFood(Long foodId) {
		return foodRepo.loadSimpleFood(foodId);
	}

	@Transactional(readOnly = true)
	public ComplexFood loadComplexFood(Long foodId, Hydrater<ComplexFood> hydrater) {
		ComplexFood food = foodRepo.loadComplexFood(foodId);
		hydrater.hydrate(food);

		return food;
	}

	@Transactional(readOnly = true)
	public Set<SimpleFood> getSimpleFoods() {
		return foodRepo.findSimpleFoods();
	}

	@Transactional(readOnly = true)
	public Set<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater) {
		Set<ComplexFood> foods = foodRepo.findComplexFoods();
		for (ComplexFood food : foods) {
			hydrater.hydrate(food);
		}

		return foods;
	}

	@Transactional(rollbackFor = { RuntimeException.class, FoodException.class })
	public void createSimpleFood(SimpleFoodPropertyProvider propertyProvider) throws FoodException {
		SimpleFood food = new SimpleFood(propertyProvider);
		simpleFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
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

	@Transactional(rollbackFor = { RuntimeException.class, FoodException.class })
	public void updateComplexFood(Long foodId, ComplexFoodMutablePropertyProvider propertyProvider)
			throws FoodException {
		ComplexFood food = foodRepo.loadComplexFood(foodId);
		food.update(propertyProvider);
		complexFoodUpdateValidator.validate(food);
	}

	@Transactional(rollbackFor = { RuntimeException.class })
	public void deleteFood(Long foodId) {
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

	public void setComplexFoodUpdateValidator(ComplexFoodValidator validator) {
		this.complexFoodUpdateValidator = validator;
	}
}
