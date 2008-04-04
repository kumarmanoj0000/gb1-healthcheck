package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.web.WebConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/foods/manageFoods.jsp")
public class ManageFoodsAction extends ActionSupport implements SessionAware {
	public static final String SIMPLE_FOODS_LIST_SESSION_KEY = ManageFoodsAction.class.getName()
			+ ".cachedSimpleFoodsList";
	public static final String COMPLEX_FOODS_LIST_SESSION_KEY = ManageFoodsAction.class.getName()
			+ ".cachedComplexFoodsList";

	private FoodService foodService;
	private Map<String, Object> sessionMap;
	private boolean refreshList;

	public ManageFoodsAction() {
	}

	@Override
	public String execute() {
		List<SimpleFood> simpleFoodList = getSimpleFoods();
		if (simpleFoodList == null || refreshList) {
			Set<SimpleFood> foods = foodService.getSimpleFoods();
			simpleFoodList = new ArrayList<SimpleFood>(foods);
			sessionMap.put(SIMPLE_FOODS_LIST_SESSION_KEY, simpleFoodList);
		}

		List<ComplexFood> complexFoodList = getComplexFoods();
		if (complexFoodList == null || refreshList) {
			Set<ComplexFood> foods = foodService
					.getComplexFoods(new IdentityHydrater<ComplexFood>());
			complexFoodList = new ArrayList<ComplexFood>(foods);
			sessionMap.put(COMPLEX_FOODS_LIST_SESSION_KEY, complexFoodList);
		}

		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List<SimpleFood> getSimpleFoods() {
		List<SimpleFood> simpleFoodList = (List<SimpleFood>) sessionMap
				.get(SIMPLE_FOODS_LIST_SESSION_KEY);
		return simpleFoodList;
	}

	@SuppressWarnings("unchecked")
	public List<ComplexFood> getComplexFoods() {
		List<ComplexFood> complexFoodList = (List<ComplexFood>) sessionMap
				.get(COMPLEX_FOODS_LIST_SESSION_KEY);
		return complexFoodList;
	}

	public int getFoodListPageSize() {
		return WebConstants.DEFAULT_PAGE_SIZE;
	}

	public void setRefreshList(boolean refreshList) {
		this.refreshList = refreshList;
	}

	public void setActionMessageKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
