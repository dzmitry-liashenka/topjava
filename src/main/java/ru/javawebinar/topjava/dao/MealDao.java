package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.web.CRUDMeals;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MealDao implements CRUDMeals<Meal, MealTo> {
    private static final Logger log = LoggerFactory.getLogger(MealDao.class);

    private Map<Long, Meal> mealsMap;
    private Map<Long, MealTo> mealsToMap;
    private List<Meal> meals;
    private List<MealTo> mealsTo;
    private int caloriesPerDay;

    {
        log.debug("Initialisation  [{}]", MealServlet.class.getSimpleName());
        meals = Collections.synchronizedList(new ArrayList<>());
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 10, 0), "Завтрак", 500, 1L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 13, 0), "Обед", 1000, 2L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 20, 0), "Ужин", 500, 3L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 0, 0), "Еда на граничное значение", 100, 4L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 10, 0), "Завтрак", 1000, 5L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 13, 0), "Обед", 500, 6L));
        meals.add(new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 20, 0), "Ужин", 410, 7L));

        caloriesPerDay = 2000;
        mealsTo = filteredByStreams(meals, caloriesPerDay);
        log.debug("Meal was initiated with test data: [{}]", mealsTo);
        mealsMap = new ConcurrentHashMap<>();
        meals.forEach(meal -> {
            mealsMap.put(meal.getId(), meal);
        });
    }

    private List<MealTo> filteredByStreams(List<Meal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }

    @Override
    public List<MealTo> add(Meal meal) {
        //TODO implement new meal with new ID
        log.debug("New Meal with ID: [{}]", meal.getId());
        meals.add(meal);
        return filteredByStreams(meals, caloriesPerDay);
    }

    @Override
    public List<MealTo> delete(Long id) {
        log.debug("Delete meal with ID : [{}]", id);
        mealsTo = filteredByStreams(meals, caloriesPerDay);
        log.debug("Test Meals from [{}]: [{}]", MealServlet.class.getSimpleName(), mealsTo);
        Iterator i = mealsTo.iterator();
        while (i.hasNext()) {
            MealTo mealTo = (MealTo) i.next();
            if (mealTo.getId().compareTo(id) == 0) {
                i.remove();
            }
        }
        return mealsTo;
    }

    @Override
    public List<MealTo> update(Meal meal) {
        Meal updatedMeal = getMealById(meal.getId());
        updatedMeal.setDateTime(meal.getDateTime());
        updatedMeal.setCalories(meal.getCalories());
        updatedMeal.setDescription(meal.getDescription());
        Iterator<Meal> iterator = meals.iterator();
        while(iterator.hasNext()) {
            Meal m = iterator.next();
            if (m.getId() == meal.getId()) {
                iterator.remove();
                meals.add(updatedMeal);
            }
        }
        return filteredByStreams(meals, caloriesPerDay); //TODO with stream and testen ob so funktioniert
    }

    @Override
    public List<MealTo> getAllMeals() {
        return filteredByStreams(meals, 2000);
    }

    @Override
    public Meal getMealById(Long id) {
        return meals
                .stream()
                .filter(meal -> (meal.getId() == id))
                .findFirst()
                .get();
    }

    public Long getMaxId() {
        return meals.stream().mapToLong(v -> v.getId()).max().orElseThrow(NoSuchElementException::new);
    }
}
