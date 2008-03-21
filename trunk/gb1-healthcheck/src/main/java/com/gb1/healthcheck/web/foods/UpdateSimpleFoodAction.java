package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
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
@ParentPackage("default")
@Results( {
		@Result(name = "input", type = ServletDispatcherResult.class, value = "/views/foods/editSimpleFood.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "listFoods", params = {
				"namespace", "/foods", "parse", "true", "actionMessageKey", "${actionMessageKey}" }) })
@Validation
public class UpdateSimpleFoodAction extends SimpleFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateSimpleFoodAction.class.getName()
			+ ".model";
	private Map<String, Object> session;

	public UpdateSimpleFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	@Override
	public String input() {
		SimpleFood food = getFoodService().loadSimpleFood(getFoodId());
		BasicSimpleFoodUpdateRequest model = new BasicSimpleFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.simpleFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			getFoodService().updateSimpleFood(getModel());
			session.remove(MODEL_SESSION_KEY);

			setActionMessageKey("foods.simpleFoods.edit.success");
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

	public BasicSimpleFoodUpdateRequest getModel() {
		return (BasicSimpleFoodUpdateRequest) session.get(MODEL_SESSION_KEY);
	}
}
