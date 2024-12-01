package com.gihan.composetutorial


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.gihan.composetutorial.gyms.prsentation.SemanticDescription
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailScreen
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailScreenState
import com.gihan.composetutorial.ui.theme.ComposeTutorialTheme
import org.junit.Rule
import org.junit.Test

class GymDetailScreenTest {


    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {

        testRule.setContent {
            ComposeTutorialTheme {
                GymDetailScreen(
                    gymDetailScreenState =
                    GymDetailScreenState(
                        gym = null,
                        isLoading = true,
                        error = null
                    )
                )

            }
        }

        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsDisplayed()

    }

    @Test
    fun loadContentState_isActive() {
        val gym = getDummyGym()
        testRule.setContent {
            ComposeTutorialTheme {
                GymDetailScreen(
                    GymDetailScreenState(
                        gym = gym,
                        isLoading = false,
                        error = null
                    )
                )

            }
        }
        testRule.onNodeWithText(gym.name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsNotDisplayed()

    }

    @Test
    fun errorState_isActive() {
        val error = "There is some error happen"
        testRule.setContent {

            ComposeTutorialTheme {
                GymDetailScreen(
                    GymDetailScreenState(
                        gym = null,
                        isLoading = false,
                        error = error
                    )
                )

            }

        }
        testRule.onNodeWithText(error).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsNotDisplayed()


    }
}