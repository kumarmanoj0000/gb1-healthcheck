package com.gb1.healthcheck.web.meals;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.healthcheck.web.WebConstants;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/meals/manageMeals.jsp")
public class ManageMealsAction extends ActionSupport implements SessionAware {
	public static final String MEAL_LIST_SESSION_KEY = ManageMealsAction.class.getName()
			+ ".cachedMealList";

	private MealService mealService;
	private Map<String, Object> sessionMap;
	private User requester;
	private boolean refreshList;

	public ManageMealsAction() {
	}

	@Override
	public String execute() {
		List<Meal> mealList = getMeals();

		if (mealList == null || refreshList) {
			mealList = mealService.getMealHistory(requester);
			sessionMap.put(MEAL_LIST_SESSION_KEY, mealList);
		}

		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List<Meal> getMeals() {
		List<Meal> mealList = (List<Meal>) sessionMap.get(MEAL_LIST_SESSION_KEY);
		return mealList;
	}

	public int getMealListPageSize() {
		return WebConstants.DEFAULT_PAGE_SIZE;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}

	public void setRefreshList(boolean refreshList) {
		this.refreshList = refreshList;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Resource
	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}
}
