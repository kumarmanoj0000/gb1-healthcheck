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
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.web.nutrition.SimpleFoodCreationRequest;

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
		SimpleFoodCreationRequest request = new SimpleFoodCreationRequest();
		request.setName("orange");
		SimpleFood food = new SimpleFood(request);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		foodRepo.saveSimpleFood(EasyMock.eq(food));
		EasyMock.expectLastCall();
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		svc.createSimpleFood(request);
		EasyMock.verify(foodRepo);
	}
}
