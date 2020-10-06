package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealController.class);

    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String LIST_MEALS = "meals.jsp";
    private static final String DELETE_ACTION = "delete";
    private static final String EDIT_ACTION = "edit";

    private MealDao mealDao;

    @Override
    public void init() {
        log.debug("Initialisation MealController");
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("doGet on " + MealController.class.getSimpleName());

        List<MealTo> mealsTo = mealDao.getAllItems();
        log.debug("Test Meals from [{}]: [{}]", MealServlet.class.getSimpleName(), mealsTo);

        String forward = "";
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase(DELETE_ACTION)) {
            log.debug("Action DELETE");
            Long mealId = Long.valueOf(req.getParameter("mealId"));
            mealsTo = mealDao.delete(mealId);
            req.setAttribute("mealsTo", mealsTo);
            forward = LIST_MEALS;
        } else if (action.equalsIgnoreCase(EDIT_ACTION)) {
            log.debug("Action EDIT ");
            Long mealId = Long.valueOf(req.getParameter("mealId"));
            Meal meal = mealDao.getItemById(mealId);
            req.setAttribute("meal", meal);
            forward = INSERT_OR_EDIT;
        } else {
            forward = INSERT_OR_EDIT;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = toLocalDateTime(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.valueOf(req.getParameter("calories"));
        String id = req.getParameter("mealId");
        Long mealId;
        List<MealTo> meals;
        if (id.equals("")) {
            mealId = mealDao.getMaxId() + 1L;
            log.debug("Insert Meal with ID: [{}]", mealId);
            Meal meal = new Meal(dateTime, description, calories, mealId);
            log.debug("doPost on MealController with Meal ID: [{}]", meal.getId());
            meals = mealDao.add(meal);
        } else {
            log.debug("Update Meal with ID: [{}]", id);
            mealId = Long.valueOf(id);
            Meal meal = new Meal(dateTime, description, calories, mealId);
            meals = mealDao.update(meal);
        }

        req.setAttribute("mealsTo", meals);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    private LocalDateTime toLocalDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(str, formatter);
    }
}
