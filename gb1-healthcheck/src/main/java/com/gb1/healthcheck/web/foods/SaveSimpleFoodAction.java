package com.gb1.healthcheck.web.foods;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/foods/simpleFood.jsp"),
		@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class SaveSimpleFoodAction extends ActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = SaveSimpleFoodAction.class.getName()
			+ ".model";

	@Resource
	protected FoodService foodService;

	private Long foodId = null;
	private Map<String, Object> sessionMap;

	public SaveSimpleFoodAction() {
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Override
	public String input() {
		SimpleFood food = (foodId == null ? new SimpleFood() : foodService.findSimpleFood(foodId));
		sessionMap.put(MODEL_SESSION_KEY, new SimpleFoodAdapter(food));

		return Action.INPUT;
	}

	public String cancel() {
		sessionMap.remove(MODEL_SESSION_KEY);
		return Action.SUCCESS;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.simpleFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			SimpleFood food = getModel().getTarget();
			if (food.getId() == null) {
				foodService.createSimpleFood(food);
			}
			else {
				foodService.updateSimpleFood(food);
			}

			sessionMap.remove(MODEL_SESSION_KEY);
			addActionMessage(getText("foods.simpleFoods.edit.success"));
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.simpleFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public SimpleFoodAdapter getModel() {
		return (SimpleFoodAdapter) sessionMap.get(MODEL_SESSION_KEY);
	}

	public List<FoodGroup> getAvailableGroups() {
		return Arrays.asList(FoodGroup.values());
	}

	public List<Nutrient> getAvailableNutrients() {
		return Arrays.asList(Nutrient.values());
	}
}
