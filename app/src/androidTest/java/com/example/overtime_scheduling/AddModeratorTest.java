package com.example.overtime_scheduling;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddModeratorTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkAddModerator_1() {
        onView(withId(R.id.login_username)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("admin123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());
        onView(withId(R.id.addModeratorCard)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.imgView)).perform(ViewActions.click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.name)).perform(typeText("Jihan"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("jihan@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.contact)).perform(typeText("458966145"), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("17/A, Aftabnagar"), closeSoftKeyboard());
        onView(withId(R.id.bloodGroup)).perform(typeText("A+"), closeSoftKeyboard());
        onView(withId(R.id.userId)).perform(typeText("m123"), closeSoftKeyboard());

        onView(withId(R.id.btnRegister)).perform(click());

        onView(ViewMatchers.withId(R.id.addModeratorPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkAddModerator_2() {
        onView(withId(R.id.login_username)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("admin123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());
        onView(withId(R.id.addModeratorCard)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.imgView)).perform(ViewActions.click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.name)).perform(typeText("Jihan Hasan"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("jihan@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.contact)).perform(typeText("01854785632"), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("17/A, Aftabnagar"), closeSoftKeyboard());
        onView(withId(R.id.bloodGroup)).perform(typeText("A+"), closeSoftKeyboard());
        onView(withId(R.id.userId)).perform(typeText("m123"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.btnRegister)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.moderatorListCard)).perform(click());

        onView(ViewMatchers.withId(R.id.moderatorListPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void checkAddModerator_3() {
        onView(withId(R.id.login_username)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("admin123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());
        onView(withId(R.id.addModeratorCard)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.imgView)).perform(ViewActions.click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.name)).perform(typeText("Riyaj Salehin"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("riyaj@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.contact)).perform(typeText("01452147854"), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("18/A, Gulshan"), closeSoftKeyboard());
        onView(withId(R.id.bloodGroup)).perform(typeText("B+"), closeSoftKeyboard());
        onView(withId(R.id.userId)).perform(typeText("m123"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.btnRegister)).perform(click());

        onView(ViewMatchers.withId(R.id.addModeratorPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}