package com.gb1.healthcheck.domain.meals;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class EmailMealInactivityNotifierTest {
	@Test
	public void testNotify() {
		Date today = new Date();
		List<User> users = Arrays.asList(Users.gb(), Users.lg());

		Meal lastMealGb = Meals.fullItalianDinner();
		lastMealGb.setInstant(DateUtils.addDays(today, -5));
		Meal lastMealLg = Meals.fullItalianDinner();
		lastMealLg.setInstant(DateUtils.addDays(today, -9));

		Map<String, String> constants = new HashMap<String, String>();
		constants.put("mealInactivity.daysThreshold", "7");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsers()).andReturn(users);
		EasyMock.replay(userRepo);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findLastMeal(Users.gb())).andReturn(lastMealGb);
		EasyMock.expect(mealRepo.findLastMeal(Users.lg())).andReturn(lastMealLg);
		EasyMock.replay(mealRepo);

		MealInactivityEmailBuilder builder = EasyMock.createMock(MealInactivityEmailBuilder.class);
		EasyMock.expect(builder.createMessage(Users.lg(), lastMealLg)).andReturn(
				new MimeMessage((Session) null));
		EasyMock.replay(builder);

		JavaMailSender mailSender = EasyMock.createMock(JavaMailSender.class);
		mailSender.send(EasyMock.isA(MimeMessage.class));
		EasyMock.expectLastCall().once();
		EasyMock.replay(mailSender);

		EmailMealInactivityNotifier notifier = new EmailMealInactivityNotifier();
		notifier.setGlobalConstants(constants);
		notifier.userRepository = userRepo;
		notifier.mealRepository = mealRepo;
		notifier.emailBuilder = builder;
		notifier.mailSender = mailSender;
		notifier.notifyUsersOfMealInactivity();

		EasyMock.verify(mailSender);
	}
}
