package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
//            meal.setUserId(meal.getUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Meal updatedMeal = repository.values().stream().filter(m -> m.getId().equals(meal.getId()))
                .filter(m2 -> m2.getUserId().equals(meal.getUserId())).findFirst().orElse(null);
        if (updatedMeal != null) {
            updatedMeal = meal;
            repository.put(meal.getId(), meal);
        }

        return updatedMeal;
    }

    @Override
    public boolean delete(int id, Integer userId) {
       return repository.values()
               .removeIf(m -> m.getId().equals(id) && m.getUserId().equals(userId));
    }

    @Override
    public Meal get(int id, Integer userId) {
        return repository.values()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .filter(meal -> meal.getUserId().equals(userId))
                .orElse(null);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values()
                .stream()
                .filter(m -> m.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

