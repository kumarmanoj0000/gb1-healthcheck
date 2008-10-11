package com.gb1.healthcheck.services.foods;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.core.Validator;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.meals.MealException;

@Service("foodService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class FoodServiceImpl implements FoodService {
	@Resource
	protected FoodRepository foodRepo;

	@Resource
	protected Validator<SimpleFood, FoodException> simpleFoodCreationValidator;

	@Resource
	protected Validator<SimpleFood, FoodException> simpleFoodUpdateValidator;

	@Resource
	protected Validator<ComplexFood, FoodException> complexFoodCreationValidator;

	@Resource
	protected Validator<ComplexFood, FoodException> complexFoodUpdateValidator;

	public FoodServiceImpl() {
	}

	@Transactional(readOnly = true)
	public SimpleFood findSimpleFood(Long foodId) {
		return foodRepo.findSimpleFood(foodId);
	}

	@Transactional(readOnly = true)
	public ComplexFood findComplexFood(Long foodId) {
		return foodRepo.findComplexFood(foodId);
	}

	@Transactional(readOnly = true)
	public List<SimpleFood> findAllSimpleFoods() {
		return foodRepo.findAllSimpleFoods();
	}

	@Transactional(readOnly = true)
	public List<ComplexFood> findAllComplexFoods() {
		return foodRepo.findAllComplexFoods();
	}

	@Transactional(readOnly = true)
	public Food findFood(Long foodId) {
		return foodRepo.findFood(foodId);
	}

	@Transactional(readOnly = true)
	public List<Food> findFoods(List<Long> foodIds) {
		List<Food> foods = new ArrayList<Food>();
		for (Long foodId : foodIds) {
			foods.add(foodRepo.findFood(foodId));
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
			foodRepo.delete(foodRepo.findFood(foodId));
		}
	}
}
