package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("listFoodsAction")
@Scope("prototype")
public class ListFoodsAction extends ActionSupport {
	private FoodService foodService;

	private List<SimpleFood> simpleFoods;
	private List<ComplexFood> complexFoods;

	public ListFoodsAction() {
	}

	public String list() {
		simpleFoods = new ArrayList<SimpleFood>(foodService.getSimpleFoods());
		Collections.sort(simpleFoods, new Food.ByNameComparator());

		complexFoods = new ArrayList<ComplexFood>(foodService
				.getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(complexFoods, new Food.ByNameComparator());

		return Action.SUCCESS;
	}

	public List<SimpleFood> getSimpleFoods() {
		return simpleFoods;
	}

	public List<ComplexFood> getComplexFoods() {
		return complexFoods;
	}

	public void setConfirmationMessageKey(String msg) {
		if (StringUtils.isNotBlank(msg)) {
			addActionMessage(getText(msg));
		}
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
