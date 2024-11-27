package com.gihan.composetutorial.gyms.prsentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailScreen
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailViewModel
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymScreen
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymViewModel
import com.gihan.composetutorial.ui.theme.ComposeTutorialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTutorialTheme {
                GymsAroundApp()

            }
        }
    }
}

@Composable
fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {


        composable(route = "gyms") {
            val vm: GymViewModel = hiltViewModel()
            GymScreen(
                state = vm.getGymsState(), onItemClick =
                { id ->
                    navController.navigate("gymDetail/$id")
                },
                onFavouriteClick = { id, oldValue ->
                    vm.setFavouriteGym(id, oldValue)
                }
            )
        }
        composable(route = "gymDetail/{gymId}", arguments = listOf(
            navArgument("gymId") {
                type = NavType.IntType
            }
        ), deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://www.gymsaround.com/details/{gymId}"
            }
        )
        ) {
//            val gymId = it.arguments?.getInt("gymId")
            val vm: GymDetailViewModel = hiltViewModel()
            GymDetailScreen(vm)
        }
    }

}

