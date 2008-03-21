package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.services.foods.FullComplexFoodHydrater;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Controller("updateComplexFoodAction")
@Scope("prototype")
@Results( {
		@Result(name = "input", value = "/views/foods/editComplexFood.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "listFoods", params = {
				"namespace", "/foods", "parse", "true", "actionMessageKey", "${actionMessageKey}" }) })
@Validation
public class UpdateComplexFoodAction extends ComplexFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateComplexFoodAction.class.getName()
			+ ".model";
	private Map<String, Object> session;

	public UpdateComplexFoodAction() {
	}

	@Override
	public String input() {
		ComplexFood food = getFoodService().loadComplexFood(getFoodId(),
				new FullComplexFoodHydrater());
		BasicComplexFoodUpdateRequest model = new BasicComplexFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.complexFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			getFoodService().updateComplexFood(getModel());
			session.remove(MODEL_SESSION_KEY);

			setActionMessageKey("foods.complexFoods.edit.success");
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.complexFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public BasicComplexFoodUpdateRequest getModel() {
		BasicComplexFoodUpdateRequest model = (BasicComplexFoodUpdateRequest) session
				.get(MODEL_SESSION_KEY);
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}
