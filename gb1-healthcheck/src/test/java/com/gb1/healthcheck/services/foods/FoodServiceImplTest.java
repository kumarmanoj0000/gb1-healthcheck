package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.core.Validator;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.SimpleFood;

public class FoodServiceImplTest {
	@Test
	public void testGetSimpleFoods() {
		List<SimpleFood> allSimpleFoods = new ArrayList<SimpleFood>(Foods.allSimpleFoods());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findAllSimpleFoods()).andReturn(allSimpleFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertTrue(CollectionUtils.isEqualCollection(allSimpleFoods, svc.findAllSimpleFoods()));
	}

	@Test
	public void testGetComplexFoods() {
		List<ComplexFood> allComplexFoods = new ArrayList<ComplexFood>(Foods.allComplexFoods());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findAllComplexFoods()).andReturn(allComplexFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertTrue(CollectionUtils.isEqualCollection(allComplexFoods, svc.findAllComplexFoods()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCreateSimpleFood() throws Exception {
		final SimpleFood food = Foods.apple();

		Validator<SimpleFood, FoodException> validator = EasyMock.createMock(Validator.class);
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
	@SuppressWarnings("unchecked")
	public void testCreateComplexFood() throws Exception {
		final ComplexFood food = Foods.spaghetti();

		Validator<ComplexFood, FoodException> validator = EasyMock.createMock(Validator.class);
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
	@SuppressWarnings("unchecked")
	public void testUpdateSimpleFood() throws Exception {
		final SimpleFood food = Foods.apple();

		Validator<SimpleFood, FoodException> validator = EasyMock.createMock(Validator.class);
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
	@SuppressWarnings("unchecked")
	public void testUpdateComplexFood() throws Exception {
		final ComplexFood food = Foods.spaghetti();

		Validator<ComplexFood, FoodException> validator = EasyMock.createMock(Validator.class);
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
		EasyMock.expect(foodRepo.findSimpleFood(foodId)).andReturn(Foods.apple());
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;

		assertEquals(Foods.apple(), svc.findSimpleFood(foodId));
	}

	@Test
	public void testDeleteSimpleFood() {
		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findFood(Foods.apple().getId())).andReturn(Foods.apple());
		foodRepo.delete(Foods.apple());
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.foodRepo = foodRepo;
		svc.deleteFoods(Arrays.asList(Foods.apple().getId()));

		EasyMock.verify(foodRepo);
	}
}
