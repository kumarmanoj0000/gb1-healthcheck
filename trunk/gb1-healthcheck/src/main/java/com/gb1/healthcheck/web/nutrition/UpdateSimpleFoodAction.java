package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class UpdateSimpleFoodAction implements SessionAware, Preparable {
	private Map<String, Object> session;
	private Long foodId = null;
	private SimpleFoodUpdateRequest model = null;
	private FoodService foodService;

	public UpdateSimpleFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	public void prepare() {
		model = (SimpleFoodUpdateRequest) session.get(modelSessionName());
	}

	public String prepareSimpleFoodUpdate() {
		SimpleFood food = foodService.loadSimpleFood(foodId);
		model = new SimpleFoodUpdateRequest(food);
		session.put(modelSessionName(), model);

		return Action.SUCCESS;
	}

	public String updateSimpleFood() throws FoodException {
		foodService.updateSimpleFood(foodId, model);
		return Action.SUCCESS;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public SimpleFoodUpdateRequest getModel() {
		return model;
	}

	public List<Group> getAvailableGroups() {
		return Arrays.asList(Group.values());
	}

	public List<Nutrient> getAvailableNutrients() {
		return Arrays.asList(Nutrient.values());
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	private String modelSessionName() {
		return getClass().getName() + ".model";
	}
}
