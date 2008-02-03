package com.gb1.healthcheck.services.foods;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodCreationRequest;
import com.gb1.healthcheck.domain.foods.ComplexFoodUpdateRequest;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodCreationRequest;
import com.gb1.healthcheck.domain.foods.SimpleFoodUpdateRequest;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;

public class FoodServiceImplTest extends TestCase {
	public void testGetSimpleFoods() {
		Set<SimpleFood> allSimpleFoods = Foods.allSimpleFoods();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findSimpleFoods()).andReturn(allSimpleFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		assertTrue(CollectionUtils.isEqualCollection(allSimpleFoods, svc.getSimpleFoods()));
	}

	@SuppressWarnings("unchecked")
	public void testGetComplexFoods() {
		Set<ComplexFood> allComplexFoods = Foods.allComplexFoods();

		Hydrater<ComplexFood> hydrater = EasyMock.createMock(Hydrater.class);
		EasyMock.expect(hydrater.hydrate(EasyMock.isA(ComplexFood.class))).andReturn(null).times(
				allComplexFoods.size());
		EasyMock.replay(hydrater);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findComplexFoods()).andReturn(allComplexFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		assertTrue(CollectionUtils
				.isEqualCollection(allComplexFoods, svc.getComplexFoods(hydrater)));
		EasyMock.verify(hydrater);
	}

	public void testCreateSimpleFood() throws Exception {
		final SimpleFood food = Foods.apple();

		SimpleFoodCreationRequest request = new SimpleFoodCreationRequest() {
			public FoodGroup getFoodGroup() {
				return food.getFoodGroup();
			}

			public String getName() {
				return food.getName();
			}

			public Set<Nutrient> getNutrients() {
				return food.getNutrients();
			}
		};

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setSimpleFoodCreationValidator(validator);
		svc.setFoodRepository(foodRepo);

		svc.createSimpleFood(request);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testCreateComplexFood() throws Exception {
		final ComplexFood food = Foods.spaghetti();

		ComplexFoodCreationRequest request = new ComplexFoodCreationRequest() {
			public String getName() {
				return food.getName();
			}

			public Set<Long> getIngredientIds() {
				Set<Long> ids = new HashSet<Long>();
				for (Food f : food.getIngredients()) {
					ids.add(f.getId());
				}
				return ids;
			}
		};

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl() {
			@Override
			protected ComplexFood createComplexFoodFromRequest(ComplexFoodCreationRequest request) {
				return food;
			}
		};
		svc.setComplexFoodCreationValidator(validator);
		svc.setFoodRepository(foodRepo);

		svc.createComplexFood(request);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateSimpleFood() throws Exception {
		final SimpleFood oldApple = Foods.apple();
		SimpleFoodUpdateRequest updateReq = new SimpleFoodUpdateRequest() {
			public FoodGroup getFoodGroup() {
				return oldApple.getFoodGroup();
			}

			public String getName() {
				return "new apple";
			}

			public Set<Nutrient> getNutrients() {
				return oldApple.getNutrients();
			}
		};

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(oldApple);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadSimpleFood(oldApple.getId())).andReturn(oldApple);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setSimpleFoodUpdateValidator(validator);
		svc.setFoodRepository(foodRepo);
		svc.updateSimpleFood(oldApple.getId(), updateReq);

		assertEquals(updateReq.getName(), oldApple.getName());
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateComplexFood() throws Exception {
		final ComplexFood oldSpag = Foods.spaghetti();

		ComplexFoodUpdateRequest updateReq = new ComplexFoodUpdateRequest() {
			public String getName() {
				return "updated spag";
			}

			public Set<Long> getIngredientIds() {
				Set<Long> ids = new HashSet<Long>();
				for (Food f : oldSpag.getIngredients()) {
					ids.add(f.getId());
				}
				return ids;
			}
		};

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(oldSpag);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadComplexFood(oldSpag.getId())).andReturn(oldSpag);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setComplexFoodUpdateValidator(validator);
		svc.setFoodRepository(foodRepo);
		svc.updateComplexFood(oldSpag.getId(), updateReq);

		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testLoadSimpleFood() {
		final Long foodId = 1L;

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadSimpleFood(foodId)).andReturn(Foods.apple());
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		assertEquals(Foods.apple(), svc.loadSimpleFood(foodId));
	}

	public void testDeleteSimpleFood() {
		final Long foodId = 1L;

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.deleteFood(foodId);
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);
		svc.deleteFood(foodId);

		EasyMock.verify(foodRepo);
	}
}
