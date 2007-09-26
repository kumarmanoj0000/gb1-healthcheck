package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

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

	public void testCreateSimpleFood() {
		SimpleFood food = Foods.apple();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveSimpleFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		svc.createSimpleFood(food);
		EasyMock.verify(foodRepo);
	}

	public void testUpdateSimpleFood() throws Exception {
		Long foodId = 1L;
		SimpleFood oldApple = new SimpleFood(Foods.apple());
		final String newName = "new apple";

		SimpleFoodPropertyProvider updateReq = new SimpleFoodPropertyProvider() {
			public Group getGroup() {
				return Foods.apple().getGroup();
			}

			public String getName() {
				return newName;
			}

			public Set<Nutrient> getNutrients() {
				return Foods.apple().getNutrients();
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadSimpleFood(foodId)).andReturn(oldApple);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);
		svc.updateSimpleFood(foodId, updateReq);

		assertEquals(newName, oldApple.getName());
		EasyMock.verify(foodRepo);
	}
}
