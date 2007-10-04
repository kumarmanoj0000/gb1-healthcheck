package com.gb1.healthcheck.web.nutrition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodHasNoIngredientsException;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CreateComplexFoodAction extends ActionSupport implements Preparable {
	private ComplexFoodCreationRequest foodCreationRequest = new ComplexFoodCreationRequest();
	private List<Food> availableIngredients = new ArrayList<Food>();
	private FoodService foodService;

	public CreateComplexFoodAction() {
	}

	public void prepare() {
		availableIngredients.clear();
		availableIngredients.addAll(foodService.getSimpleFoods());
		availableIngredients.addAll(foodService.getComplexFoods());
		Collections.sort(availableIngredients, new Food.ByNameComparator());
	}

	public String input() {
		return Action.SUCCESS;
	}

	public String submit() {
		String result = Action.INPUT;

		try {
			foodService.createComplexFood(foodCreationRequest);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
		}
		catch (ComplexFoodHasNoIngredientsException e) {
			addFieldError("model.name", "You must select at least one ingredient.");
		}
		catch (FoodException e) {
			addActionError(e.getMessage());
		}

		return result;
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public ComplexFoodCreationRequest getModel() {
		return foodCreationRequest;
	}

	public List<Food> getAvailableIngredients() {
		return Collections.unmodifiableList(availableIngredients);
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
