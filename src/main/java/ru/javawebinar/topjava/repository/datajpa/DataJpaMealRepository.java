package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
//@Profile("datajpa")
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_BY_DATETIME = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudMealRepository;

    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.findById(userId).orElse(null);
        if (meal.getId() != null) {
            Optional<Meal> opt = crudMealRepository.findByIdAndUserId(meal.getId(), userId);
            if (opt.isPresent()) {
                Meal dbMeal = opt.get();
                meal.setId(dbMeal.getId());
                meal.setUser(user);
                crudMealRepository.save(meal);
            } else {
                return null;
            }
        } else {
            if(user != null) {
                meal.setUser(user);
                crudMealRepository.save(meal);
            } else {
                return null;
            }

        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Optional<Meal> dbMeal = crudMealRepository.findByIdAndUserId(id, userId);
        if (dbMeal.isPresent()) {
            crudMealRepository.delete(dbMeal.get().getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> opt = Optional.ofNullable(crudMealRepository.findByIdAndUserId(id, userId).orElse(null));
        return opt.isPresent() ? opt.get() : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findAllByUserId(userId, SORT_BY_DATETIME);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(userId, startDateTime, endDateTime.minusSeconds(1));
    }
}
