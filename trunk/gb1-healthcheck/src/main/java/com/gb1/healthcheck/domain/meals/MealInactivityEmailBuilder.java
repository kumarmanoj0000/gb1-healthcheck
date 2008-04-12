package com.gb1.healthcheck.domain.meals;

import javax.mail.internet.MimeMessage;

import com.gb1.healthcheck.domain.users.User;

public interface MealInactivityEmailBuilder {
	MimeMessage createMessage(User user, Meal lastMeal);
}
