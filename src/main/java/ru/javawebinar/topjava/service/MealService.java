package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.management.ValueExp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    //TODO 5.4: Сохранить/обновить еду, параметр запроса - Meal.
    // Если обновляемая еда с этим id чужая или отсутствует - NotFoundException
    public void create(Meal meal, int userId) {
        log.info("create new Meal for UserId: [{}]", meal.getUserId());
        if (meal.getUserId() != userId) {
            ValidationUtil.checkNotFound(false, "userId [" + meal.getUserId() + "]");
        }
        repository.save(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update Meal with id: [{}]", id);
        checkNotFoundWithId(repository.save(meal), id);
    }

    public void delete(int id, int userId) {
        log.info("delete Meal with id: [{}]", id);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        log.info("get Meal with id: [{}]", id);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        log.info("getAll for UserId: [{}]", userId);
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDay);
    }

    public List<MealTo> getAll(int userId, int caloriesPerDay, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll with filter: [{}] - [{}] : [{}] - [{}] for UserId [{}]" , startDate, endDate, startTime, endTime, userId);
        List<MealTo> mealTos = getAll(userId, caloriesPerDay);
        mealTos = mealTos
                .stream()
                .filter(mealTo -> DateTimeUtil.isBetween(mealTo.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());

        mealTos = mealTos
                .stream()
                .filter(mealTo -> DateTimeUtil.isBetween(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        return mealTos;
    }
}