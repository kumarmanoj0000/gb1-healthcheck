package com.gb1.healthcheck.web.meals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;

public class ListMealsAction implements ServletRequestAware {
	private HttpServletRequest request;
	private MealService mealService;

	private List<Meal> mealHistory = new LinkedList<Meal>();

	public ListMealsAction() {
	}

	public String list() {
		mealHistory.addAll(mealService.getMealHistory(getRequester()));
		return Action.SUCCESS;
	}

	protected User getRequester() {
		return HttpRequestUtils.getUser(request);
	}

	public List<Meal> getMealHistory() {
		return Collections.unmodifiableList(mealHistory);
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}
}
