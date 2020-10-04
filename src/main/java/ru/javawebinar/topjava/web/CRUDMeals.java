package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

/***
 *
 * @param <T> Meal, User
 * @param <M> MealTo, UserTo
 */
public interface CRUDMeals<T, M> {

    List<M> add(T t);

    List<M> delete(Long id);

    List<M> update(T t);

    List<M> getAllMeals();

    T getMealById(Long id);
}
