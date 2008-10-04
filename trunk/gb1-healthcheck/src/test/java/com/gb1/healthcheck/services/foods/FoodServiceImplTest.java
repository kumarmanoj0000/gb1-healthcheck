package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodValidator;
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
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.persist(food);
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

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		final FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.persist(food);
		EasyMock.expectLastCall().once();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.complexFoodCreationValidator = validator;
		svc.foodRepo = foodRepo;

		svc.createComplexFood(food);
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
		final ComplexFood food = Foods.spaghetti();

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		final FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.merge(food);
		EasyMock.expectLastCall().once();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.complexFoodUpdateValidator = validator;
		svc.foodRepo = foodRepo;
		svc.updateComplexFood(food);

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
		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadFood(Foods.apple().getId())).andReturn(Foods.apple());
		foodRepo.delete(Foods.apple());
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;
		svc.deleteFoods(Arrays.asList(Foods.apple().getId()));

		EasyMock.verify(foodRepo);
	}
}
