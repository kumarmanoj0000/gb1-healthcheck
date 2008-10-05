package com.gb1.healthcheck.services.foods;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.Hydrater;

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
		return hydrater.hydrate(food);
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

	@Transactional(readOnly = true)
	public Food getFood(Long foodId) {
		return foodRepo.loadFood(foodId);
	}

	@Transactional(readOnly = true)
	public List<Food> getFoods(List<Long> foodIds) {
		List<Food> foods = new ArrayList<Food>();
		for (Long foodId : foodIds) {
			foods.add(foodRepo.loadFood(foodId));
		}

		return foods;
	}

	public void createSimpleFood(SimpleFood food) throws FoodException {
		simpleFoodCreationValidator.validate(food);
		foodRepo.persist(food);
	}

	public void createComplexFood(ComplexFood food) throws FoodException {
		complexFoodCreationValidator.validate(food);
		foodRepo.persist(food);
	}

	public void updateSimpleFood(SimpleFood food) throws FoodException {
		simpleFoodUpdateValidator.validate(food);
		foodRepo.merge(food);
	}

	public void updateComplexFood(ComplexFood food) throws FoodException {
		complexFoodUpdateValidator.validate(food);
		foodRepo.merge(food);
	}

	public void deleteFoods(List<Long> foodIds) {
		for (Long foodId : foodIds) {
			foodRepo.delete(foodRepo.loadFood(foodId));
		}
	}
}
