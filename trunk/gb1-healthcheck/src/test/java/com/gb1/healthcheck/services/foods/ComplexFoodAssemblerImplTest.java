package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodRepository;
import com.gb1.healthcheck.domain.foods.Foods;

public class ComplexFoodAssemblerImplTest {
	@Test
	public void testCreate() {
		final ComplexFood reference = Foods.spaghetti();

		ComplexFoodCreationRequest request = new ComplexFoodCreationRequest() {
			public String getName() {
				return reference.getName();
			}

			public Set<Long> getIngredientIds() {
				Set<Long> ingredientIds = new HashSet<Long>();
				for (Food ingredient : reference.getIngredients()) {
					ingredientIds.add(ingredient.getId());
				}
				return ingredientIds;
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (Food ingredient : reference.getIngredients()) {
			EasyMock.expect(foodRepo.loadFood(ingredient.getId())).andReturn(ingredient);
		}
		EasyMock.replay(foodRepo);

		ComplexFoodAssemblerImpl assembler = new ComplexFoodAssemblerImpl();
		assembler.foodRepo = foodRepo;

		ComplexFood food = assembler.createComplexFood(request);
		assertEquals(reference, food);
		assertTrue(CollectionUtils.isEqualCollection(reference.getIngredients(), food
				.getIngredients()));
	}

	@Test
	public void testUpdate() {
		// no more beef & beef broth!

		final ComplexFood food = Foods.spaghetti();
		final String updatedName = food.getName() + " (vegetarian)";
		final Set<Long> updatedIngredientIds = new HashSet<Long>();
		updatedIngredientIds.add(Foods.tomato().getId());
		updatedIngredientIds.add(Foods.pasta().getId());

		ComplexFoodUpdateRequest request = new ComplexFoodUpdateRequest() {
			public Long getFoodId() {
				return food.getId();
			}

			public String getName() {
				return updatedName;
			}

			public Set<Long> getIngredientIds() {
				return updatedIngredientIds;
			}
		};

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		EasyMock.expect(foodRepo.loadFood(Foods.tomato().getId())).andReturn(Foods.tomato());
		EasyMock.expect(foodRepo.loadFood(Foods.pasta().getId())).andReturn(Foods.pasta());
		EasyMock.replay(foodRepo);

		ComplexFoodAssemblerImpl assembler = new ComplexFoodAssemblerImpl();
		assembler.foodRepo = foodRepo;
		assembler.updateComplexFood(food, request);

		assertEquals(request.getName(), food.getName());
		assertEquals(request.getIngredientIds().size(), food.getIngredients().size());
		assertEquals(request.getIngredientIds().iterator().next(), food.getIngredients().iterator()
				.next().getId());
	}
}
