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
import com.gb1.healthcheck.services.foods.support.ComplexFoodCreationPropertyProviderAdapter;
import com.gb1.healthcheck.services.foods.support.ComplexFoodUpdatePropertyProviderAdapter;

@Service("foodService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
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

	public void createSimpleFood(SimpleFoodCreationRequest request) throws FoodException {
		SimpleFood food = createSimpleFoodFromRequest(request);
		simpleFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
	}

	protected SimpleFood createSimpleFoodFromRequest(SimpleFoodCreationRequest request) {
		return new SimpleFood(request);
	}

	public void createComplexFood(ComplexFoodCreationRequest request) throws FoodException {
		ComplexFood food = createComplexFoodFromRequest(request);
		complexFoodCreationValidator.validate(food);
		foodRepo.saveFood(food);
	}

	protected ComplexFood createComplexFoodFromRequest(ComplexFoodCreationRequest request) {
		return new ComplexFood(new ComplexFoodCreationPropertyProviderAdapter(request));
	}

	public void updateSimpleFood(SimpleFoodUpdateRequest request) throws FoodException {
		SimpleFood food = foodRepo.loadSimpleFood(request.getFoodId());
		food.update(request);
		simpleFoodUpdateValidator.validate(food);
	}

	public void updateComplexFood(ComplexFoodUpdateRequest request) throws FoodException {
		ComplexFood food = foodRepo.loadComplexFood(request.getFoodId());
		food.update(new ComplexFoodUpdatePropertyProviderAdapter(request));
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
	public void setSimpleFoodCreationValidator(SimpleFoodValidator validator) {
		this.simpleFoodCreationValidator = validator;
	}

	@Resource
	public void setSimpleFoodUpdateValidator(SimpleFoodValidator validator) {
		this.simpleFoodUpdateValidator = validator;
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
