package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodValidator;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodCreationValidator;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodUpdateValidator;

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

	public void testGetComplexFoods() {
		Set<ComplexFood> allComplexFoods = Foods.allComplexFoods();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findComplexFoods()).andReturn(allComplexFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		assertTrue(CollectionUtils.isEqualCollection(allComplexFoods, svc.getComplexFoods()));
	}

	public void testCreateSimpleFood() throws Exception {
		SimpleFood food = Foods.apple();

		SimpleFoodCreationValidator validator = EasyMock
				.createMock(SimpleFoodCreationValidator.class);
		validator.validate(food);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveSimpleFood(EasyMock.eq(food));
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
			public Group getGroup() {
				return oldApple.getGroup();
			}

			public String getName() {
				return "new apple";
			}

			public Set<Nutrient> getNutrients() {
				return oldApple.getNutrients();
			}
		};

		SimpleFoodUpdateValidator validator = EasyMock.createMock(SimpleFoodUpdateValidator.class);
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
		svc.deleteSimpleFood(foodId);

		EasyMock.verify(foodRepo);
	}
}
