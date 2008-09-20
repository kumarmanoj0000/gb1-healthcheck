package com.gb1.healthcheck.domain.meals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

@Component("emailInactivityNotifier")
public class EmailMealInactivityNotifier implements MealInactivityNotifier {
	private UserRepository userRepository;
	private MealRepository mealRepository;
	private JavaMailSender mailSender;
	private MealInactivityEmailBuilder emailBuilder;
	private int inactiveDaysThreshold;

	public EmailMealInactivityNotifier() {
	}

	public void notifyUsersOfMealInactivity() {
		Date cutDate = DateUtils.addDays(new Date(), -inactiveDaysThreshold);
		List<User> users = userRepository.findUsers();
		List<MimeMessage> toSend = new ArrayList<MimeMessage>();

		for (User user : users) {
			Meal lastMeal = mealRepository.getLastMealBy(user);
			if (lastMeal == null || lastMeal.getInstant().before(cutDate)) {
				MimeMessage msg = emailBuilder.createMessage(user, lastMeal);
				toSend.add(msg);
			}
		}

		for (MimeMessage msg : toSend) {
			mailSender.send(msg);
		}
	}

	@Resource
	public void setUserRepository(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Resource
	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepository = mealRepo;
	}

	@Resource
	public void setJavaMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Resource
	public void setUserInactivityEmailBuilder(MealInactivityEmailBuilder builder) {
		this.emailBuilder = builder;
	}

	@Resource
	public void setGlobalConstants(Map<String, String> constants) {
		inactiveDaysThreshold = Integer.parseInt(constants.get("mealInactivity.daysThreshold"));
	}
}
