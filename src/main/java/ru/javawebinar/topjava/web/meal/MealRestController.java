package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void update(Meal meal) {
        service.update(meal, meal.getId());
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    public void create(Meal meal) {
        service.create(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return service.getAll(SecurityUtil.authUserId(), MealsUtil.DEFAULT_CALORIES_PER_DAY, fromDate, toDate, fromTime, toTime);
    }

    public List<MealTo> getAll() {
        return service.getAll(SecurityUtil.authUserId(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}