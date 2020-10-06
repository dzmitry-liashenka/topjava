package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

/***
 *
 * @param <T> Meal, User
 * @param <M> MealTo, UserTo
 */
public interface Crudable<T, M> {

    List<M> add(T t);

    List<M> delete(Long id);

    List<M> update(T t);

    List<M> getAllItems();

    T getItemById(Long id);
}
