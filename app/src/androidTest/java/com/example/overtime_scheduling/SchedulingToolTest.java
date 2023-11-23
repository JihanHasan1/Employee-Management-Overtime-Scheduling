package com.example.overtime_scheduling;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.DatePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchedulingToolTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkScheduling_1() {
        onView(withId(R.id.login_username)).perform(typeText("m123"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.schedulingCard)).perform(click());

        onView(withId(R.id.etOvertime)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.etDate)).perform(click());

        // Set a date on the DatePicker.
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 9, 20));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Click on the OK button to close the DatePicker.
        onView(withText("OK")).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.btnApply)).perform(click());
        onView(ViewMatchers.withId(R.id.schedulingToolPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkScheduling_2() {
        onView(withId(R.id.login_username)).perform(typeText("m123"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());
        onView(withId(R.id.schedulingCard)).perform(click());

        onView(withId(R.id.etOvertime)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.etDate)).perform(click());

        // Set a date on the DatePicker.
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 9, 20));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Click on the OK button to close the DatePicker.
        onView(withText("OK")).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.btnApply)).perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.overtimeCard)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.btnBack)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.logoutCard)).perform(click());
        onView(withId(R.id.login_username)).perform(typeText("e123"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.login_signin)).perform(click());
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.overtimeCard)).perform(click());

        onView(ViewMatchers.withId(R.id.overtimeListPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}