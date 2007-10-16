package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.gb1.commons.dao.Hydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodValidator;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodGroup;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodValidator;

public class FoodServiceImplTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		PlatformTransactionManager txManager = EasyMock
				.createNiceMock(PlatformTransactionManager.class);
		AnnotationTransactionAspect.aspectOf().setTransactionManager(txManager);
		EasyMock.replay(txManager);
	}

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
		SimpleFood food = Foods.apple();

		SimpleFoodValidator validator = EasyMock.createMock(SimpleFoodValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setSimpleFoodCreationValidator(validator);
		svc.setFoodRepository(foodRepo);

		svc.createSimpleFood(food);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testCreateComplexFood() throws Exception {
		ComplexFood food = Foods.spaghetti();

		ComplexFoodValidator validator = EasyMock.createMock(ComplexFoodValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setComplexFoodCreationValidator(validator);
		svc.setFoodRepository(foodRepo);

		svc.createComplexFood(food);
		EasyMock.verify(validator);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateSimpleFood() throws Exception {
		final SimpleFood oldApple = Foods.apple();
		SimpleFoodMutablePropertyProvider updateReq = new SimpleFoodMutablePropertyProvider() {
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

		ComplexFoodMutablePropertyProvider updateReq = new ComplexFoodMutablePropertyProvider() {
			public Set<Food> getIngredients() {
				return oldSpag.getIngredients();
			}

			public String getName() {
				return "updated spag";
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
