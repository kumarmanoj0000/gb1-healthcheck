package com.gb1.healthcheck.services.foods;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;
import com.gb1.healthcheck.domain.meals.MealException;

@Service("foodService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class FoodServiceImpl implements FoodService {
	private FoodRepository foodRepo;

	private SimpleFoodAssembler simpleFoodAssembler;
	private SimpleFoodValidator simpleFoodCreationValidator;
	private SimpleFoodValidator simpleFoodUpdateValidator;
	private ComplexFoodAssembler complexFoodAssembler;
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

	public void createSimpleFood(SimpleFoodCreationRequest request) throws FoodException {
		SimpleFood food = simpleFoodAssembler.create(request);
		simpleFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
	}

	public void createComplexFood(ComplexFoodCreationRequest request) throws FoodException {
		ComplexFood food = complexFoodAssembler.create(request);
		complexFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
	}

	public void updateSimpleFood(SimpleFoodUpdateRequest request) throws FoodException {
		SimpleFood food = foodRepo.loadSimpleFood(request.getFoodId());
		simpleFoodAssembler.update(food, request);
		simpleFoodUpdateValidator.validate(food);
	}

	public void updateComplexFood(ComplexFoodUpdateRequest request) throws FoodException {
		ComplexFood food = foodRepo.loadComplexFood(request.getFoodId());
		complexFoodAssembler.update(food, request);
		complexFoodUpdateValidator.validate(food);
	}

	public void deleteFoods(Set<Long> foodIds) {
		foodRepo.deleteFoods(foodIds);
	}

	@Resource
	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}

	@Resource
	public void setSimpleFoodAssembler(SimpleFoodAssembler simpleFoodAssembler) {
		this.simpleFoodAssembler = simpleFoodAssembler;
	}

	@Resource
	public void setSimpleFoodCreationValidator(SimpleFoodValidator validator) {
		this.simpleFoodCreationValidator = validator;
	}

	@Resource
	public void setSimpleFoodUpdateValidator(SimpleFoodValidator validator) {
		this.simpleFoodUpdateValidator = validator;
	}

	@Resource
	public void setComplexFoodAssembler(ComplexFoodAssembler complexFoodAssembler) {
		this.complexFoodAssembler = complexFoodAssembler;
	}

	@Resource
	public void setComplexFoodCreationValidator(ComplexFoodValidator validator) {
		this.complexFoodCreationValidator = validator;
	}

	@Resource
	public void setComplexFoodUpdateValidator(ComplexFoodValidator validator) {
		this.complexFoodUpdateValidator = validator;
	}
}
