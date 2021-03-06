package com.gudigudigudi.appdemojetpack.motionlayout;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.gudigudigudi.appdemojetpack.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isBottomAlignedWith;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftAlignedWith;
import static androidx.test.espresso.assertion.PositionAssertions.isRightAlignedWith;
import static androidx.test.espresso.assertion.PositionAssertions.isTopAlignedWith;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MotionLayoutDemoActivityTest {
    @Rule
    public ActivityScenarioRule<MotionLayoutDemoActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MotionLayoutDemoActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void viewInited() {
        // ImageView checks.
        onView(withId(R.id.ball)).check(matches(isDisplayed()));
//                .check(matches(withDrawableId(R.drawable.ic_create)));

        int[] imageViewIds = {R.id.middle, R.id.left, R.id.right, R.id.windmill};
        for (int id : imageViewIds) {
            onView(withId(id)).check(matches(isDisplayed()));
            onView(withId(id)).check(matches(MyViewMatchers.withDrawableId(R.drawable.ic_arrow_back)));
        }

        // TextView checks.
        onView(withId(R.id.tipText)).check(matches(isDisplayed())).check(matches(withText("Swipe the blue android icon up")));
        onView(withId(R.id.tipText2)).check(matches(isEnabled())).check(matches(withText("")));

        checkViewsPositionOriginal();
    }

    @Test
    public void swipeUpMoreThanHalf() {
        // 向上滑动超过界面一半高度
        ViewAction action = new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER, GeneralLocation.CENTER, Press.FINGER);
        onView(withId(R.id.root)).perform(action);

        checkViewsPositionSwiped();
    }

    @Test
    public void swipeDownMoreThanHalf() {
        // 向上滑动超过界面一半高度后，再向下滑动超过界面一半高度
        ViewAction actionUp = new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER, GeneralLocation.CENTER, Press.FINGER);
        ViewAction actionDown = new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER, GeneralLocation.BOTTOM_CENTER, Press.FINGER);
        onView(withId(R.id.root)).perform(actionUp, actionDown);

        checkViewsPositionOriginal();
    }

    public void checkViewsPositionOriginal() {
        onView(withId(R.id.ball)).check(isCompletelyLeftOf(withId(R.id.tipText)));
        onView(withId(R.id.windmill)).check(isCompletelyLeftOf(withId(R.id.middle)));

        onView(withId(R.id.left)).check(isLeftAlignedWith(withId(R.id.middle)))
                .check(isRightAlignedWith(withId(R.id.middle)))
                .check(isTopAlignedWith(withId(R.id.middle)))
                .check(isBottomAlignedWith(withId(R.id.middle)));
        onView(withId(R.id.left)).check(isLeftAlignedWith(withId(R.id.right)))
                .check(isRightAlignedWith(withId(R.id.right)))
                .check(isTopAlignedWith(withId(R.id.right)))
                .check(isBottomAlignedWith(withId(R.id.right)));
    }

    public void checkViewsPositionSwiped() {
        onView(withId(R.id.ball)).check(isCompletelyBelow(withId(R.id.tipText)));
        onView(withId(R.id.windmill)).check(isCompletelyBelow(withId(R.id.tipText)));

        onView(withId(R.id.left)).check(isCompletelyLeftOf(withId(R.id.middle)))
                .check(isTopAlignedWith(withId(R.id.middle)))
                .check(isBottomAlignedWith(withId(R.id.middle)));
        onView(withId(R.id.middle)).check(isCompletelyLeftOf(withId(R.id.right)))
                .check(isTopAlignedWith(withId(R.id.right)))
                .check(isBottomAlignedWith(withId(R.id.right)));
    }


}