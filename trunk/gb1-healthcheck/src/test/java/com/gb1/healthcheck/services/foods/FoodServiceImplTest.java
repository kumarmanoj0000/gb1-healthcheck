package com.gb1.healthcheck.services.foods;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;
import com.gb1.healthcheck.services.foods.support.ComplexFoodAssembler;
import com.gb1.healthcheck.services.foods.support.SimpleFoodAssembler;

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

		SimpleFoodAssembler assembler = EasyMock.createMock(SimpleFoodAssembler.class);
		EasyMock.expect(assembler.create(request)).andReturn(food);
		EasyMock.replay(assembler);

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setSimpleFoodAssembler(assembler);
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

		ComplexFoodAssembler assembler = EasyMock.createMock(ComplexFoodAssembler.class);
		EasyMock.expect(assembler.create(request)).andReturn(food);
		EasyMock.replay(assembler);

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		final FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall().once();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setComplexFoodAssembler(assembler);
		svc.setComplexFoodCreationValidator(validator);
		svc.setFoodRepository(foodRepo);

		svc.createComplexFood(request);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateSimpleFood() throws Exception {
		final SimpleFood oldApple = Foods.apple();

		SimpleFoodUpdateRequest updateReq = new SimpleFoodUpdateRequest() {
			public Long getFoodId() {
				return oldApple.getId();
			}

			public FoodGroup getFoodGroup() {
				return oldApple.getFoodGroup();
			}

			public String getName() {
				return oldApple.getName() + " (updated)";
			}

			public Set<Nutrient> getNutrients() {
				return oldApple.getNutrients();
			}
		};

		SimpleFoodAssembler assembler = EasyMock.createMock(SimpleFoodAssembler.class);
		assembler.update(oldApple, updateReq);
		EasyMock.expectLastCall();
		EasyMock.replay(assembler);

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(oldApple);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadSimpleFood(oldApple.getId())).andReturn(oldApple);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setSimpleFoodAssembler(assembler);
		svc.setSimpleFoodUpdateValidator(validator);
		svc.setFoodRepository(foodRepo);
		svc.updateSimpleFood(updateReq);

		EasyMock.verify(assembler);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateComplexFood() throws Exception {
		final ComplexFood oldSpag = Foods.spaghetti();

		ComplexFoodUpdateRequest updateReq = new ComplexFoodUpdateRequest() {
			public Long getFoodId() {
				return oldSpag.getId();
			}

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

		ComplexFoodAssembler assembler = EasyMock.createMock(ComplexFoodAssembler.class);
		assembler.update(oldSpag, updateReq);
		EasyMock.expectLastCall();
		EasyMock.replay(assembler);

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(oldSpag);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		final FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadComplexFood(oldSpag.getId())).andReturn(oldSpag);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setComplexFoodAssembler(assembler);
		svc.setComplexFoodUpdateValidator(validator);
		svc.setFoodRepository(foodRepo);
		svc.updateComplexFood(updateReq);

		EasyMock.verify(assembler);
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
		Set<Long> foodIds = new HashSet<Long>();
		foodIds.add(Foods.apple().getId());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.deleteFoods(foodIds);
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);
		svc.deleteFoods(foodIds);

		EasyMock.verify(foodRepo);
	}
}
