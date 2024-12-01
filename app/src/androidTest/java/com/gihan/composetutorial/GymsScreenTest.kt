package com.gihan.composetutorial

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gihan.composetutorial.gyms.prsentation.SemanticDescription
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymScreen
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymsScreenState
import com.gihan.composetutorial.ui.theme.ComposeTutorialTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {


    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {
            ComposeTutorialTheme {
                GymScreen(state = GymsScreenState(
                    emptyList(), true, null
                ), onItemClick = {}, onFavouriteClick = { _: Int, _: Boolean -> })
            }

        }
        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsDisplayed()

    }

    @Test
    fun loadContentState_isActive() {
        val gymList = getDummyGymsData()
        testRule.setContent {
            GymScreen(
                state = GymsScreenState(gymList, false, null),
                onItemClick = {},
                onFavouriteClick = { _: Int, _: Boolean -> })
        }
        testRule.onNodeWithText(gymList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsNotDisplayed()

    }


    @Test
    fun errorState_isActive() {
        val errorMessage = "Sorry Some Error happen"
        testRule.setContent {
            GymScreen(
                state = GymsScreenState(emptyList(), false, errorMessage),
                onItemClick = {},
                onFavouriteClick = { _: Int, _: Boolean -> })
        }
        testRule.onNodeWithText(errorMessage).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsNotDisplayed()


    }

    @Test
    fun onItemClick_isPassedIDCorrectly() {
        val gymList = getDummyGymsData()
        val clickedGym = gymList[0]
        testRule.setContent {
            GymScreen(
                state = GymsScreenState(gymList, false, null),
                onItemClick = { id ->
                    assert(id == clickedGym.id)

                },
                onFavouriteClick = { _: Int, _: Boolean -> })
        }
        testRule.onNodeWithText(clickedGym.name).performClick()
    }
}