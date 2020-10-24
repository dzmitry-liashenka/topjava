package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew() ) {
            meal.setUser(em.find(User.class, userId));
            em.persist(meal);
            return meal;
        } else {
            Meal updated = em.find(Meal.class, meal.getId());
            Integer dbUserId = updated.getUser().getId();
            if (dbUserId == userId) {
                User user = em.find(User.class, userId);
                meal.setUser(user);
                Meal merged = em.merge(meal);
                return merged;
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter(1, id)
                .setParameter(2, user)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal != null) {
            if (!meal.getUser().getId().equals(userId)) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, user)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED_BETWEEN, Meal.class)
                .setParameter(1, user)
                .setParameter(2, startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();
    }
}