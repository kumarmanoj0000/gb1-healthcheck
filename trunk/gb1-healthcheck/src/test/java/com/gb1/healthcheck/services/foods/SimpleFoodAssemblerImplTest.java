package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;

public class SimpleFoodAssemblerImplTest {
	@Test
	public void testCreate() {
		final SimpleFood oldFood = Foods.apple();
		SimpleFoodCreationRequest request = new SimpleFoodCreationRequest() {
			public String getName() {
				return oldFood.getName();
			}

			public FoodGroup getFoodGroup() {
				return oldFood.getFoodGroup();
			}

			public Set<Nutrient> getNutrients() {
				return oldFood.getNutrients();
			}
		};

		SimpleFoodAssemblerImpl assembler = new SimpleFoodAssemblerImpl();
		SimpleFood food = assembler.createSimpleFood(request);

		assertEquals(oldFood.getName(), food.getName());
		assertEquals(oldFood.getFoodGroup(), food.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(oldFood.getNutrients(), food.getNutrients()));
	}

	@Test
	public void testUpdate() {
		final SimpleFood food = Foods.apple();
		SimpleFoodUpdateRequest request = new SimpleFoodUpdateRequest() {
			public Long getFoodId() {
				return food.getId();
			}

			public FoodGroup getFoodGroup() {
				return food.getFoodGroup();
			}

			public String getName() {
				return "updated apple";
			}

			public Set<Nutrient> getNutrients() {
				return Collections.singleton(Nutrient.VITAMIN_C);
			}
		};

		SimpleFoodAssemblerImpl assembler = new SimpleFoodAssemblerImpl();
		assembler.updateSimpleFood(food, request);

		assertEquals("updated apple", food.getName());
		assertEquals(food.getFoodGroup(), food.getFoodGroup());
		assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Nutrient.VITAMIN_C),
				food.getNutrients()));
	}
}
