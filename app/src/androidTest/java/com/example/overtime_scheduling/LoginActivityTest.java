package com.example.overtime_scheduling;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.junit.Assert.*;

import androidx.test.rule.ActivityTestRule;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkLogin_1() {
        onView(withId(R.id.login_signin)).perform(click());

        onView(ViewMatchers.withId(R.id.loginPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkLogin_2() {
        onView(withId(R.id.login_username)).perform(typeText("anything"), closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText("1234567"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.login_signin)).perform(click());

        onView(ViewMatchers.withId(R.id.loginPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkLogin_3() {
        onView(withId(R.id.login_username)).perform(typeText("admin"), closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText("admin123"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.login_signin)).perform(click());

        onView(ViewMatchers.withId(R.id.mainPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}