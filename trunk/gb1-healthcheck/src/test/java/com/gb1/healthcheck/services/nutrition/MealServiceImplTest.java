package com.gb1.healthcheck.services.nutrition;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealRepository;
import com.gb1.healthcheck.domain.nutrition.MealValidator;
import com.gb1.healthcheck.domain.nutrition.Meals;

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
		Collections.sort(sortedMealHistory, new Meal.ByDateAndTimeComparator());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeals()).andReturn(mealHistory);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		assertTrue(CollectionUtils.isEqualCollection(sortedMealHistory, svc.getMealHistory()));
	}

	public void testCreateMeal() throws MealException {
		final Meal meal = new Meal(new Date());
		meal.addDish(Meals.spaghettiDish());
		meal.addDish(Meals.redWineDrink());

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
}
