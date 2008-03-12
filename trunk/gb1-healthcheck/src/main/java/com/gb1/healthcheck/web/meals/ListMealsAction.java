package com.gb1.healthcheck.web.meals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
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
public class ListMealsAction extends ActionSupport implements ServletRequestAware {
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

	public void setActionMessageKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Resource
	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}
}
