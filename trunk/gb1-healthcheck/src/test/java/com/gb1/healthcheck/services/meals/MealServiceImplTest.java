package com.gb1.healthcheck.services.meals;

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
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealCreationPropertyProviderAdapter;
import com.gb1.healthcheck.domain.meals.MealCreationRequest;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.meals.MealUpdateRequest;
import com.gb1.healthcheck.domain.meals.MealValidator;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparedFoodCreationRequest;
import com.gb1.healthcheck.domain.meals.PreparedFoodUpdateRequest;
import com.gb1.healthcheck.domain.users.UserRepository;

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

		final MealCreationPropertyProviderAdapter adapter = new MealCreationPropertyProviderAdapter(
				createReq);
		adapter.setUserRepository(userRepo);

		MealServiceImpl svc = new MealServiceImpl() {
			@Override
			protected MealCreationPropertyProviderAdapter createMealCreationPropertyProviderAdapter(
					MealCreationRequest request) {
				return adapter;
			}
		};
		svc.setMealRepository(mealRepo);
		svc.setMealCreationValidator(validator);

		svc.createMeal(createReq);

		EasyMock.verify(validator);
		EasyMock.verify(mealRepo);
	}

	public void testUpdateMeal() throws MealException {
		Meal oldMeal = Meals.fullItalianDinner();

		MealUpdateRequest updateReq = new MealUpdateRequest() {
			public Date getInstant() {
				return new Date();
			}

			public Set<PreparedFoodUpdateRequest> getDishUpdateRequests() {
				return Collections.emptySet();
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
