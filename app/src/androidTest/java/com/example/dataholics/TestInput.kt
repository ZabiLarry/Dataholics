package com.example.dataholics

import android.app.Activity
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dataholics.database.Task
import com.example.dataholics.ui.input.InputFragment
import com.example.dataholics.ui.input.InputViewModel
import kotlinx.android.synthetic.main.fragment_input.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestInput {

    lateinit var input : InputFragment

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        val scenario = launchFragmentInContainer<InputFragment>()
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context

    }


    @Test
    fun addTaskTest(){
        input = InputFragment()
        //by pressing sleeping alone at 21:00 on 5/5/2020

        onView(withId(R.id.sleep)).perform((click()))
        onView(withId(R.id.alone)).perform((click()))
        onView(withId(R.id.durationTime)).perform((typeText("1")))

        val task = Task(2020050521,1, 1)
        val tasktest = input.addTask(1,1,20200505, 21, 1)
        assertEquals(tasktest.TaskId, task.TaskId)
    }


    @Test
    fun selectChoiceTest(){
        input = InputFragment()
        var choice = input.selectChoice(R.id.eating)
        assertEquals("2131230854", choice)

        choice = input.selectChoice(R.id.partner)
        assertEquals("2131230986", choice)



    }
    @Test
    fun makeId(){
        input = InputFragment()
        val date = 2020050521
        val dateTest : Int = input.getDate(21,20200505)
        assertEquals(dateTest, date)
    }

}