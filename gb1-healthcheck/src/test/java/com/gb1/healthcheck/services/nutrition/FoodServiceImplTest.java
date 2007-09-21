package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;

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

	public void testGetComplexFoods() {
		Set<ComplexFood> allComplexFoods = Foods.allComplexFoods();

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.findComplexFoods()).andReturn(allComplexFoods);
		EasyMock.replay(foodRepo);

		FoodServiceImpl svc = new FoodServiceImpl();
		svc.setFoodRepository(foodRepo);

		assertTrue(CollectionUtils.isEqualCollection(allComplexFoods, svc.getComplexFoods()));
	}
}
