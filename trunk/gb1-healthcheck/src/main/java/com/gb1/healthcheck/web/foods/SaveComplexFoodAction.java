package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodHasNoIngredientsException;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/foods/complexFood.jsp"),
		@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class SaveComplexFoodAction extends ActionSupport implements SessionAware, Preparable {
	protected static final String MODEL_SESSION_KEY = SaveComplexFoodAction.class.getName()
			+ ".model";

	@Resource
	protected FoodService foodService;

	private Long foodId;
	private Map<String, Object> sessionMap;
	private List<Food> availableIngredients = new ArrayList<Food>();

	public SaveComplexFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public void prepare() throws Exception {
		availableIngredients.addAll(foodService.findAllSimpleFoods());
		availableIngredients.addAll(foodService.findAllComplexFoods());
		Collections.sort(availableIngredients, new Food.ByNameComparator());
	}

	@Override
	public String input() {
		ComplexFood food = (foodId == null ? new ComplexFood() : foodService
				.findComplexFood(foodId));
		sessionMap.put(MODEL_SESSION_KEY, new ComplexFoodBuilder(food));

		return Action.INPUT;
	}

	public String cancel() {
		sessionMap.remove(MODEL_SESSION_KEY);
		return Action.SUCCESS;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.complexFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			ComplexFood food = getModel().build(foodService);
			if (food.getId() == null) {
				foodService.createComplexFood(food);
			}
			else {
				foodService.updateComplexFood(food);
			}

			sessionMap.remove(MODEL_SESSION_KEY);
			addActionMessage(getText("foods.complexFoods.edit.success"));
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (ComplexFoodHasNoIngredientsException e) {
			addFieldError("model.name", getText("food.exception.selectAtLeastOneIngredient"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.complexFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public ComplexFoodBuilder getModel() {
		return (ComplexFoodBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	public List<Food> getAvailableIngredients() {
		return availableIngredients;
	}
}
