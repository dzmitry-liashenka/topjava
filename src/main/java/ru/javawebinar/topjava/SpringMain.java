package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName", "email@mail.by", "password2", Role.ADMIN));
            System.out.println(adminUserController.getAll());

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! mealRestController !!!!!!!!!!!!!!!!!!!!!!!!");

            Meal meal1 = new Meal(LocalDateTime.now().minusDays(1), "testDescription", 888);
            Meal meal2 = new Meal(LocalDateTime.now().minusDays(1), "testDescription", 567);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(meal1);
            mealRestController.create(meal2);
            System.out.println("getAll: " + mealRestController.getAll());
           // update meal1 with id=8
            Meal meal3 = new Meal(8, LocalDateTime.now().minusDays(1), "updated description", 777, 1);
            mealRestController.update(meal3);
            System.out.println("get All für heute: " + mealRestController.getAll(LocalDate.now().minusDays(2), LocalDate.now(), LocalTime.now().minusHours(1), LocalTime.now()));
//            mealRestController.delete(10);
            System.out.println("Meal with id 8: " + mealRestController.get(8));

            MealService mealService = appCtx.getBean(MealService.class);
            Meal mealWithFalseUserId = new Meal(null, LocalDateTime.now().minusDays(1), "new meal with false userId, expected NPE", 333, 2);
//            mealService.create(mealWithFalseUserId, 1);
            System.out.println("get All für heute2: " + mealRestController.getAll(LocalDate.now().minusDays(2), LocalDate.now(), LocalTime.now().minusHours(1), LocalTime.now()));

        }
    }
}
