package com.gb1.healthcheck.services.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealInactivityNotifier;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.meals.MealValidator;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class MealServiceImplTest {
	@Test
	public void testGetMealHistory() {
		final User eater = Users.gb();
		final List<Meal> mealHistory = Meals.mealHistory();

		List<Meal> sortedMealHistory = new LinkedList<Meal>(mealHistory);
		Collections.sort(sortedMealHistory, new Meal.ByInstantComparator());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(eater)).andReturn(mealHistory);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealRepo = mealRepo;

		assertTrue(CollectionUtils.isEqualCollection(sortedMealHistory, svc.getMealHistory(eater)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testLoadMeal() {
		Meal meal = Meals.fullItalianDinner();

		Hydrater<Meal> hydrater = EasyMock.createMock(Hydrater.class);
		EasyMock.expect(hydrater.hydrate(meal)).andReturn(meal);
		EasyMock.replay(hydrater);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeal(meal.getId())).andReturn(meal);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealRepo = mealRepo;

		assertEquals(meal, svc.getMeal(meal.getId(), hydrater));
		EasyMock.verify(hydrater);
	}

	@Test
	public void testCreateMeal() throws MealException {
		final Meal meal = Meals.fullItalianDinner();

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(meal.getEater().getId())).andReturn(meal.getEater());
		EasyMock.replay(userRepo);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.persistMeal(meal);
		EasyMock.expectLastCall();
		EasyMock.replay(mealRepo);

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(meal);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealRepo = mealRepo;
		svc.mealCreationValidator = validator;

		svc.createMeal(meal);

		EasyMock.verify(validator);
		EasyMock.verify(mealRepo);
	}

	@Test
	public void testUpdateMeal() throws MealException {
		final Meal meal = Meals.fullItalianDinner();

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(meal);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.mergeMeal(meal);
		EasyMock.expectLastCall();
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealRepo = mealRepo;
		svc.mealUpdateValidator = validator;

		svc.updateMeal(meal);

		EasyMock.verify(validator);
	}

	@Test
	public void testDeleteMeals() {
		Set<Long> mealIds = new HashSet<Long>();
		mealIds.add(Meals.fullItalianDinner().getId());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeal(Meals.fullItalianDinner().getId())).andReturn(
				Meals.fullItalianDinner());
		mealRepo.deleteMeal(Meals.fullItalianDinner());
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealRepo = mealRepo;

		svc.deleteMeals(mealIds);
		EasyMock.verify(mealRepo);
	}

	@Test
	public void testNotifyInactiveUsers() {
		MealInactivityNotifier notifier = EasyMock.createMock(MealInactivityNotifier.class);
		notifier.notifyUsersOfMealInactivity();
		EasyMock.expectLastCall().once();
		EasyMock.replay(notifier);

		MealServiceImpl svc = new MealServiceImpl();
		svc.mealInactivityNotifier = notifier;
		svc.notifyUsersOfMealInactivity();

		EasyMock.verify(notifier);
	}
}
