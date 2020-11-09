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
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Controller
public class JpaMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JpaMealController.class);

    public JpaMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String doGet(Model model, HttpServletRequest request) {
        log.debug("doGet on [{}]", JpaMealController.class.getSimpleName());
        Map<String, String[]> parameterMap = request.getParameterMap();

        if (!parameterMap.isEmpty()) {
            if (parameterMap.containsKey("action")) {
                String action = request.getParameter("action");
                Integer id = getId(request);
                switch (action == null ? "all" : action) {
                    case "delete" -> {
                        super.delete(id);
                        model.addAttribute("meals", super.getAll());
                        return "meals";
                    }
                    case "create", "update" -> {
                        final Meal meal = "create".equals(action) ?
                                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                                super.get(id);
                        model.addAttribute("meal", meal);
                        return "mealForm";
                    }
                    case "filter" -> {
                        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
                        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
                        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
                        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

                        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
                        return "meals";
                    }
                    default -> {
                        model.addAttribute("meals", super.getAll());
                        return "meals";
                    }
                }
            }

        }

        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping("/meals")
    public String doPost(Model model, HttpServletRequest request) throws IOException {
        log.debug("doPost on [{}]", JpaMealController.class.getSimpleName());
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        Integer id = getId(request);

        if (id == null) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        model.addAttribute("meals", super.getAll());
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
