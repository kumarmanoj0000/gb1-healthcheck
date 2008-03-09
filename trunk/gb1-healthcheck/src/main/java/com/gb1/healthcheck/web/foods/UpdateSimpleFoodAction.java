package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Controller("updateSimpleFoodAction")
@Scope("prototype")
@Validation
public class UpdateSimpleFoodAction extends SimpleFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateSimpleFoodAction.class.getName()
			+ ".model";

	private Map<String, Object> session;
	private Long foodId = null;
	private String confirmationMessage;

	public UpdateSimpleFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	@Override
	public String input() {
		SimpleFood food = getFoodService().loadSimpleFood(foodId);
		BasicSimpleFoodUpdateRequest model = new BasicSimpleFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.simpleFoods.update.error.nameRequired") })
	public String submit() {
		String result = Action.INPUT;

		try {
			getFoodService().updateSimpleFood(getModel());
			session.remove(MODEL_SESSION_KEY);

			confirmationMessage = getText("foods.simpleFoods.update.success");
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.simpleFoods.create.error",
					new String[] { e.getMessage() }));
		}

		return result;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public BasicSimpleFoodUpdateRequest getModel() {
		return (BasicSimpleFoodUpdateRequest) session.get(MODEL_SESSION_KEY);
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}
}
