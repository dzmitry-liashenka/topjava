package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
public class JpaMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JpaMealController.class);

    public JpaMealController(MealService service) {
        super(service);
    }

    @GetMapping("/create")
    public String create (Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update (Model model, HttpServletRequest request) {
        Meal meal = super.get(getId(request));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals")
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping("/meal")
    public String createOrUpdateMeal(Model model, HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        Integer id = getId(request);
        if (id == null) {
            log.info("create Meal [{}]", meal);
            super.create(meal);
        } else {
            log.info("update Meal [{}]", meal);
            super.update(meal, id);
        }

        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
        super.delete(getId(request));
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(Model model, HttpServletRequest request) {
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private Integer getId(HttpServletRequest request) {
        String paramId = request.getParameter("id");
        if (paramId == null || "".equals(paramId)) {
            return null;
        } else {
            return Integer.valueOf(paramId);
        }
    }
}
