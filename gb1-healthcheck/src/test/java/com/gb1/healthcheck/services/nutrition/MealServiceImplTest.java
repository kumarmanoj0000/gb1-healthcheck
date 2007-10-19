package com.gb1.healthcheck.services.nutrition;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.MealRepository;
import com.gb1.healthcheck.domain.nutrition.MealValidator;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;

public class MealServiceImplTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		PlatformTransactionManager txManager = EasyMock
				.createNiceMock(PlatformTransactionManager.class);
		AnnotationTransactionAspect.aspectOf().setTransactionManager(txManager);
		EasyMock.replay(txManager);
	}

	public void testGetMealHistory() {
		final List<Meal> mealHistory = Meals.mealHistory();
		List<Meal> sortedMealHistory = new LinkedList<Meal>(mealHistory);
		Collections.sort(sortedMealHistory, new Meal.ByInstantComparator());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeals()).andReturn(mealHistory);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		assertTrue(CollectionUtils.isEqualCollection(sortedMealHistory, svc.getMealHistory()));
	}

	@SuppressWarnings("unchecked")
	public void testLoadMeal() {
		Meal meal = Meals.fullItalianDinner();

		Hydrater<Meal> hydrater = EasyMock.createMock(Hydrater.class);
		EasyMock.expect(hydrater.hydrate(meal)).andReturn(meal);
		EasyMock.replay(hydrater);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeal(meal.getId())).andReturn(meal);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		assertEquals(meal, svc.loadMeal(meal.getId(), hydrater));
		EasyMock.verify(hydrater);
	}

	public void testCreateMeal() throws MealException {
		Meal meal = Meals.fullItalianDinner();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.saveMeal(EasyMock.eq(meal));
		EasyMock.expectLastCall();
		EasyMock.replay(mealRepo);

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(EasyMock.eq(meal));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);
		svc.setMealCreationValidator(validator);

		svc.createMeal(meal);

		EasyMock.verify(validator);
		EasyMock.verify(mealRepo);
	}

	public void testUpdateMeal() throws MealException {
		Meal oldMeal = Meals.fullItalianDinner();

		MealMutablePropertyProvider updateReq = new MealMutablePropertyProvider() {
			public Set<PreparedFood> getDishes() {
				return Collections.emptySet();
			}

			public Date getInstant() {
				return new Date();
			}
		};

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(oldMeal);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeal(oldMeal.getId())).andReturn(oldMeal);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);
		svc.setMealUpdateValidator(validator);

		svc.updateMeal(oldMeal.getId(), updateReq);

		EasyMock.verify(validator);
	}

	public void testDeleteMeal() {
		final Long mealId = 1L;

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.deleteMeal(mealId);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		svc.deleteMeal(mealId);
		EasyMock.verify(mealRepo);
	}
}
