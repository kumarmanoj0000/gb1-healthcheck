package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;
import org.springframework.mail.javamail.JavaMailSender;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class EmailUserInactivityNotifierTest extends TestCase {
	public void testNotify() {
		Date today = new Date();

		Set<User> users = new LinkedHashSet<User>();
		users.add(Users.gb());
		users.add(Users.lg());

		Meal lastMealGb = Meals.fullItalianDinner();
		lastMealGb.setInstant(DateUtils.addDays(today, -5));
		Meal lastMealLg = Meals.fullItalianDinner();
		lastMealLg.setInstant(DateUtils.addDays(today, -9));

		Map<String, String> constants = new HashMap<String, String>();
		constants.put("user.inactiveDaysThreshold", "7");

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.findUsers()).andReturn(users);
		EasyMock.replay(userRepo);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.getLastMealBy(Users.gb())).andReturn(lastMealGb);
		EasyMock.expect(mealRepo.getLastMealBy(Users.lg())).andReturn(lastMealLg);
		EasyMock.replay(mealRepo);

		UserInactivityEmailBuilder builder = EasyMock.createMock(UserInactivityEmailBuilder.class);
		EasyMock.expect(builder.createMessage(Users.lg(), lastMealLg)).andReturn(
				new MimeMessage((Session) null));
		EasyMock.replay(builder);

		JavaMailSender mailSender = EasyMock.createMock(JavaMailSender.class);
		mailSender.send(EasyMock.isA(MimeMessage.class));
		EasyMock.expectLastCall().once();
		EasyMock.replay(mailSender);

		EmailUserInactivityNotifier notifier = new EmailUserInactivityNotifier();
		notifier.setGlobalConstants(constants);
		notifier.setUserRepository(userRepo);
		notifier.setMealRepository(mealRepo);
		notifier.setUserInactivityEmailBuilder(builder);
		notifier.setJavaMailSender(mailSender);
		notifier.notifyInactiveUsers();

		EasyMock.verify(mailSender);
	}
}
