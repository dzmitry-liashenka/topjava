package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private List<MealTo> mealsTo;

    @Override
    public void init() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.AUGUST, 31, 20, 0), "Ужин", 410)
        );

        mealsTo = filteredByStreams(meals, 2000);
        System.out.println(mealsTo);

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
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
//        resp.sendRedirect("meals.jsp");
    }
}
