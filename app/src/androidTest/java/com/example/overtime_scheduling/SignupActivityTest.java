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
public class SignupActivityTest {
    @Rule
    public ActivityTestRule<SignupActivity> activityRule = new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void checkSignup_1() {
        onView(withId(R.id.signup)).perform(click());

        onView(ViewMatchers.withId(R.id.signupPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkSignup_2() {
        onView(withId(R.id.username)).perform(typeText("anything"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("1234567"), closeSoftKeyboard());

        onView(withId(R.id.rePassword)).perform(typeText("1234567"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.signup)).perform(click());

        onView(ViewMatchers.withId(R.id.signupPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkSignup_3() {
        onView(withId(R.id.username)).perform(typeText("m123"), closeSoftKeyboard());

        onView(withId(R.id.password)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.rePassword)).perform(typeText("123"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.signup)).perform(click());

        onView(withId(R.id.login_username)).perform(typeText("m123"), closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText("123"), closeSoftKeyboard());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.login_signin)).perform(click());

        onView(ViewMatchers.withId(R.id.mainPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}