package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

@Controller("createMealAction")
@Scope("prototype")
@ParentPackage("default")
@Results( {
		@Result(name = "input", type = ServletDispatcherResult.class, value = "/views/meals/editMeal.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "listMeals", params = {
				"namespace", "/meals", "parse", "true", "actionMessageKey", "${actionMessageKey}" }) })
public class CreateMealAction extends MealActionSupport implements Preparable {
	private MealCreationRequest model;

	public CreateMealAction() {
	}

	public void prepare() {
		loadAvailableFoods();
		model = new BasicMealCreationRequest(getRequester());
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			getMealService().createMeal(model);
			setActionMessageKey("meals.edit.success");
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

	public MealCreationRequest getModel() {
		return model;
	}
}
