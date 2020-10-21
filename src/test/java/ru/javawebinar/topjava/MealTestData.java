package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 3;
    public static final int MEAL_ID_UPDATE = 2;
    public static final int USER_ID_NOT_EXIST = 100002;

    public static final Meal meal4 = new Meal(4,
            LocalDateTime.of(2020, 10, 20, 07, 15, 00, 000000),
            "frühstückNachsteTag", 1111);

    public static final Meal meal1 = new Meal(3,
            LocalDateTime.of(2020, 10, 19, 18, 00, 00, 000000),
            "abendessen", 500);

    public static final Meal meal2 = new Meal(2,
            LocalDateTime.of(2020, 10, 19, 12, 00, 00, 000000),
            "mittag", 1500);

    public static final Meal meal3 = new Meal(1,
            LocalDateTime.of(2020, 10, 19, 07, 00, 00, 000000),
            "frühstück", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, List<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static Meal getNew() {
        return new Meal(null, now(), "NewMeal", 1234);
    }

    public static Meal getUpdated() {
        meal2.setDescription("updated");
        meal2.setCalories(321);
        return meal2;
    }

    public static Meal[] getExpectedBetween() {
        Meal[] meals = {meal1, meal2, meal3};
        return meals;
    }
}
