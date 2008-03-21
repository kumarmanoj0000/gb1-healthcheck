package com.gb1.healthcheck.web.meals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("listMealsAction")
@Scope("prototype")
@ParentPackage("default")
@Result(type = ServletDispatcherResult.class, value = "/views/meals/listMeals.jsp")
public class ListMealsAction extends ActionSupport {
	private MealService mealService;

	private List<Meal> mealHistory = new LinkedList<Meal>();

	public ListMealsAction() {
	}

	@Override
	public String execute() {
		mealHistory.addAll(mealService.getMealHistory(getRequester()));
		return Action.SUCCESS;
	}

	protected User getRequester() {
		return HttpRequestUtils.getUser();
	}

	public List<Meal> getMealHistory() {
		return Collections.unmodifiableList(mealHistory);
	}

	public void setActionMessageKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
	}

	@Resource
	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}
}
