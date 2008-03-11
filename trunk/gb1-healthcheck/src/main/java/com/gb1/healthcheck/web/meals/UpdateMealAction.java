package com.gb1.healthcheck.web.meals;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.healthcheck.services.meals.MealUpdateRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

@Controller("updateMealAction")
@Scope("prototype")
public class UpdateMealAction extends MealActionSupport implements Preparable, ServletRequestAware,
		SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateMealAction.class.getName() + ".model";

	private HttpServletRequest request;
	private Map<String, Object> session;
	private Long mealId;
	private String confirmationMessageKey;

	public UpdateMealAction() {
	}

	public void prepare() throws Exception {
		loadAvailableFoods();
	}

	@Override
	public String input() {
		Meal meal = getMealService().loadMeal(mealId, new FullMealHydrater());
		session.put(MODEL_SESSION_KEY, new BasicMealUpdateRequest(meal));

		return Action.INPUT;
	}

	public String update() {
		String result = Action.INPUT;

		try {
			getMealService().updateMeal(getModel());
			session.remove(MODEL_SESSION_KEY);

			confirmationMessageKey = "meals.update.success";
			result = Action.SUCCESS;
		}
		catch (MealException e) {
			addActionError(getText("meals.update.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public Long getEaterId() {
		return getRequester(request).getId();
	}

	public MealUpdateRequest getModel() {
		MealUpdateRequest model = (MealUpdateRequest) session.get(MODEL_SESSION_KEY);
		return model;
	}

	public String getConfirmationMessageKey() {
		return confirmationMessageKey;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}
