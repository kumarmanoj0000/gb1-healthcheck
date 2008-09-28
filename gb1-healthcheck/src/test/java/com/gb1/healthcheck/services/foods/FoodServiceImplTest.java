package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodValidator;

public class FoodServiceImplTest {
	@Test
	public void testGetSimpleFoods() {
		List<SimpleFood> allSimpleFoods = new ArrayList<SimpleFood>(Foods.allSimpleFoods());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findSimpleFoods()).andReturn(allSimpleFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertTrue(CollectionUtils.isEqualCollection(allSimpleFoods, svc.getSimpleFoods()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetComplexFoods() {
		List<ComplexFood> allComplexFoods = new ArrayList<ComplexFood>(Foods.allComplexFoods());

		Hydrater<ComplexFood> hydrater = EasyMock.createMock(Hydrater.class);
		EasyMock.expect(hydrater.hydrate(EasyMock.isA(ComplexFood.class))).andReturn(null).times(
				allComplexFoods.size());
		EasyMock.replay(hydrater);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findComplexFoods()).andReturn(allComplexFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertTrue(CollectionUtils
				.isEqualCollection(allComplexFoods, svc.getComplexFoods(hydrater)));
		EasyMock.verify(hydrater);
	}

	@Test
	public void testCreateSimpleFood() throws Exception {
		final SimpleFood food = Foods.apple();

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.persist(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.simpleFoodCreationValidator = validator;
		svc.foodRepo = foodRepo;

		svc.createSimpleFood(food);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	@Test
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
		EasyMock.expect(assembler.createComplexFood(request)).andReturn(food);
		EasyMock.replay(assembler);

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		final FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.persist(EasyMock.eq(food));
		EasyMock.expectLastCall().once();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.complexFoodAssembler = assembler;
		svc.complexFoodCreationValidator = validator;
		svc.foodRepo = foodRepo;

		svc.createComplexFood(request);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	@Test
	public void testUpdateSimpleFood() throws Exception {
		final SimpleFood food = Foods.apple();

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.merge(food);
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.simpleFoodUpdateValidator = validator;
		svc.foodRepo = foodRepo;
		svc.updateSimpleFood(food);

		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	@Test
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
		assembler.updateComplexFood(oldSpag, updateReq);
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
		svc.complexFoodAssembler = assembler;
		svc.complexFoodUpdateValidator = validator;
		svc.foodRepo = foodRepo;
		svc.updateComplexFood(updateReq);

		EasyMock.verify(assembler);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	@Test
	public void testLoadSimpleFood() {
		final Long foodId = 1L;

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadSimpleFood(foodId)).andReturn(Foods.apple());
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertEquals(Foods.apple(), svc.getSimpleFood(foodId));
	}

	@Test
	public void testDeleteSimpleFood() {
		Set<Long> foodIds = new HashSet<Long>();
		foodIds.add(Foods.apple().getId());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadFood(Foods.apple().getId())).andReturn(Foods.apple());
		foodRepo.delete(Foods.apple());
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;
		svc.deleteFoods(foodIds);

		EasyMock.verify(foodRepo);
	}
}
