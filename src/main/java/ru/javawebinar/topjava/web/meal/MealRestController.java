package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    //    @GetMapping("/filter")
    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public List<MealTo> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                   @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                   @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                   @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate != null ? startDate : LocalDate.ofYearDay(1970, 1), startTime != null ? startTime : LocalTime.MIN,
                endDate != null ? endDate : LocalDate.ofYearDay(3000, 1), endTime != null ? endTime : LocalTime.MAX);
    }
}