package com.gb1.healthcheck.services.foods;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;
import com.gb1.healthcheck.domain.meals.MealException;

@Service("foodService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class FoodServiceImpl implements FoodService {
	@Resource
	protected FoodRepository foodRepo;

	@Resource
	protected SimpleFoodValidator simpleFoodCreationValidator;

	@Resource
	protected SimpleFoodValidator simpleFoodUpdateValidator;

	@Resource
	protected ComplexFoodAssembler complexFoodAssembler;

	@Resource
	protected ComplexFoodValidator complexFoodCreationValidator;

	@Resource
	protected ComplexFoodValidator complexFoodUpdateValidator;

	public FoodServiceImpl() {
	}

	@Transactional(readOnly = true)
	public SimpleFood getSimpleFood(Long foodId) {
		return foodRepo.loadSimpleFood(foodId);
	}

	@Transactional(readOnly = true)
	public ComplexFood getComplexFood(Long foodId, Hydrater<ComplexFood> hydrater) {
		ComplexFood food = foodRepo.loadComplexFood(foodId);
		hydrater.hydrate(food);

		return food;
	}

	@Transactional(readOnly = true)
	public List<SimpleFood> getSimpleFoods() {
		return foodRepo.findSimpleFoods();
	}

	@Transactional(readOnly = true)
	public List<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater) {
		List<ComplexFood> foods = foodRepo.findComplexFoods();
		for (ComplexFood food : foods) {
			hydrater.hydrate(food);
		}

		return foods;
	}

	public void createSimpleFood(SimpleFood food) throws FoodException {
		simpleFoodCreationValidator.validate(food);
		foodRepo.persist(food);
	}

	public void createComplexFood(ComplexFoodCreationRequest request) throws FoodException {
		ComplexFood food = complexFoodAssembler.createComplexFood(request);
		complexFoodCreationValidator.validate(food);
		foodRepo.persist(food);
	}

	public void updateSimpleFood(SimpleFood food) throws FoodException {
		simpleFoodUpdateValidator.validate(food);
		foodRepo.merge(food);
	}

	public void updateComplexFood(ComplexFoodUpdateRequest request) throws FoodException {
		ComplexFood food = foodRepo.loadComplexFood(request.getFoodId());
		complexFoodAssembler.updateComplexFood(food, request);
		complexFoodUpdateValidator.validate(food);
	}

	public void deleteFoods(Set<Long> foodIds) {
		for (Long foodId : foodIds) {
			Food food = foodRepo.loadFood(foodId);
			foodRepo.delete(food);
		}
	}
}
