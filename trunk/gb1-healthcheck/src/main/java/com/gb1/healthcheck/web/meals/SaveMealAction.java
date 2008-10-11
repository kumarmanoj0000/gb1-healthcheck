package com.gb1.healthcheck.web.meals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;
import org.apache.struts2.interceptor.AuthenticatedUser;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.IdentityHydrater;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/meal.jsp"),
		@Result(type = FlashResult.class, value = "manageMeals", params = { "namespace", "/meals",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class SaveMealAction extends ActionSupport implements SessionAware, Preparable {
	protected static final String MODEL_SESSION_KEY = SaveMealAction.class.getName() + ".model";

	@Resource
	protected FoodService foodService;

	@Resource
	protected MealService mealService;

	private Long mealId;
	protected User requester;
	private Map<String, Object> sessionMap;
	private List<Food> availableFoods = new ArrayList<Food>();

	public SaveMealAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public void prepare() {
		availableFoods.addAll(foodService.findAllSimpleFoods());
		availableFoods.addAll(foodService.findAllComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	@Override
	public String input() {
		Meal meal = (mealId == null ? new Meal().setEater(requester) : mealService.findMeal(mealId,
				new FullMealHydrater()));
		MealBuilder model = new MealBuilder(meal);
		sessionMap.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	public String cancel() {
		sessionMap.remove(MODEL_SESSION_KEY);
		return Action.SUCCESS;
	}

	public void addDish(HttpSession session) {
		((MealBuilder) session.getAttribute(MODEL_SESSION_KEY)).addDish();
	}

	public int removeDish(HttpSession session, int dishIndex) {
		((MealBuilder) session.getAttribute(MODEL_SESSION_KEY)).removeDish(dishIndex);
		return dishIndex;
	}

	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "model.instant", message = "", key = "meals.edit.error.instantRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			Meal meal = getModel().build(foodService);
			if (meal.getId() == null) {
				mealService.createMeal(meal);
			}
			else {
				mealService.updateMeal(meal);
			}

			sessionMap.remove(MODEL_SESSION_KEY);
			addActionMessage(getText("meals.edit.success"));
			result = Action.SUCCESS;
		}
		catch (MealAlreadyExistsException e) {
			addFieldError("model.instant", getText("meal.error.alreadyExists"));
		}
		catch (MealException e) {
			addActionError(getText("meals.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public MealBuilder getModel() {
		return (MealBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	public List<Food> getAvailableFoods() {
		return availableFoods;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		return Arrays.asList(PreparationMethod.values());
	}
}
