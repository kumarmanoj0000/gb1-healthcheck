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

public class UpdateSimpleFoodAction implements SessionAware {
	private Map<String, Object> session;
	private Long foodId = null;
	private FoodService foodService;

	public UpdateSimpleFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	public String prepareSimpleFoodUpdate() {
		SimpleFood food = foodService.loadSimpleFood(foodId);
		SimpleFoodUpdateRequest model = new SimpleFoodUpdateRequest(food);
		session.put(modelSessionKey(), model);

		return Action.SUCCESS;
	}

	public String updateSimpleFood() throws FoodException {
		foodService.updateSimpleFood(foodId, getModel());
		session.remove(modelSessionKey());

		return Action.SUCCESS;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public SimpleFoodUpdateRequest getModel() {
		return (SimpleFoodUpdateRequest) session.get(modelSessionKey());
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

	private String modelSessionKey() {
		return getClass().getName() + ".model";
	}
}
