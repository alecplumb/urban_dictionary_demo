package org.aplumb.abpurbandictionary

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.reactivex.Observable
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import org.aplumb.abpurbandictionary.api.model.Definition
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    @Mock
    lateinit var repository: UrbanDictionaryRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as UrbanDictionaryApp
        val testAppComponent =
            DaggerTestApplicationComponent.builder()
                .testMainModule(TestMainModule(repository))
                .build()
        app._component = testAppComponent
        `when`(repository.getDefinition(any())).thenReturn(
            Observable.just(
                listOf(
                    Definition(
                        "definition",
                        10,
                        "word",
                        5
                    )
                )
            )
        )
        activityRule.launchActivity(null)
    }

    @After
    fun teardown() {
        activityRule.finishActivity()
    }

    @Test
    fun repositoryIsCalled() {
        // Context of the app under test.
        onView(withId(R.id.searchTerm))
            .perform(typeText("s"))
        verify(repository, times(1)).getDefinition(any())
    }
}
