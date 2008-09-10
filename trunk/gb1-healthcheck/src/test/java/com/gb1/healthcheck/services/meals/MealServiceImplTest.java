package com.gb1.healthcheck.services.meals;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

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

public class MealServiceImplTest extends TestCase {
	public void testGetMealHistory() {
		final User eater = Users.gb();
		final List<Meal> mealHistory = Meals.mealHistory();

		List<Meal> sortedMealHistory = new LinkedList<Meal>(mealHistory);
		Collections.sort(sortedMealHistory, new Meal.ByInstantComparator());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(eater)).andReturn(mealHistory);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		assertTrue(CollectionUtils.isEqualCollection(sortedMealHistory, svc.getMealHistory(eater)));
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
		final Meal meal = Meals.fullItalianDinner();

		MealCreationRequest createReq = new MealCreationRequest() {
			public Long getEaterId() {
				return meal.getEater().getId();
			}

			public Date getInstant() {
				return meal.getInstant();
			}

			public Set<PreparedFoodCreationRequest> getDishCreationRequests() {
				return Collections.emptySet();
			}
		};

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(meal.getEater().getId())).andReturn(meal.getEater());
		EasyMock.replay(userRepo);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.saveMeal(EasyMock.eq(meal));
		EasyMock.expectLastCall();
		EasyMock.replay(mealRepo);

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(EasyMock.eq(meal));
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealAssembler assembler = EasyMock.createMock(MealAssembler.class);
		EasyMock.expect(assembler.createMeal(createReq)).andReturn(meal);
		EasyMock.replay(assembler);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealAssembler(assembler);
		svc.setMealRepository(mealRepo);
		svc.setMealCreationValidator(validator);

		svc.createMeal(createReq);

		EasyMock.verify(validator);
		EasyMock.verify(mealRepo);
	}

	public void testUpdateMeal() throws MealException {
		final Meal oldMeal = Meals.fullItalianDinner();

		MealUpdateRequest updateReq = new MealUpdateRequest() {
			public Long getMealId() {
				return oldMeal.getId();
			}

			public Date getInstant() {
				return new Date();
			}

			public Set<PreparedFoodUpdateRequest> getDishUpdateRequests() {
				return Collections.emptySet();
			}
		};

		MealAssembler mealAssembler = EasyMock.createMock(MealAssembler.class);
		mealAssembler.updateMeal(oldMeal, updateReq);
		EasyMock.expectLastCall();
		EasyMock.replay(mealAssembler);

		MealValidator validator = EasyMock.createMock(MealValidator.class);
		validator.validate(oldMeal);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.loadMeal(oldMeal.getId())).andReturn(oldMeal);
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealAssembler(mealAssembler);
		svc.setMealRepository(mealRepo);
		svc.setMealUpdateValidator(validator);

		svc.updateMeal(updateReq);

		EasyMock.verify(validator);
	}

	public void testDeleteMeals() {
		Set<Long> mealIds = new HashSet<Long>();
		mealIds.add(Meals.fullItalianDinner().getId());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		mealRepo.deleteMeals(mealIds);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mealRepo);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealRepository(mealRepo);

		svc.deleteMeals(mealIds);
		EasyMock.verify(mealRepo);
	}

	public void testNotifyInactiveUsers() {
		MealInactivityNotifier notifier = EasyMock.createMock(MealInactivityNotifier.class);
		notifier.notifyUsersOfMealInactivity();
		EasyMock.expectLastCall().once();
		EasyMock.replay(notifier);

		MealServiceImpl svc = new MealServiceImpl();
		svc.setMealInactivityNotifier(notifier);
		svc.notifyUsersOfMealInactivity();

		EasyMock.verify(notifier);
	}
}
