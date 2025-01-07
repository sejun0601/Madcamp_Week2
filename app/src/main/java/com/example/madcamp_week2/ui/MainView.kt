package com.example.madcamp_week2.ui

import WaitingViewModel
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.madcamp_week2.HomeViewModel
import com.example.madcamp_week2.MainViewModel
import com.example.madcamp_week2.MatchHistoryViewModel
import com.example.madcamp_week2.ProfileViewModel
import com.example.madcamp_week2.SearchViewModel


@Composable
fun MainView(){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val viewModel :MainViewModel = viewModel()
    val videoData =  viewModel.videoState.value

    val currentScreen = remember { viewModel.currentScreen.value}
    val title = remember { mutableStateOf(currentScreen.title) }

    val bottomBar: @Composable () -> Unit = {
        NavigationBar(
            Modifier.wrapContentSize(),
            containerColor =if (currentRoute == Screen.BottomScreen.Home.bRoute) {
                Color.Black
            } else {
                Color.White
            },
            contentColor = if (currentRoute == Screen.BottomScreen.Home.bRoute) {
                Color.White
            } else {
                Color.Black
            },
            tonalElevation = 8.dp
        ){
            screensInBottomBar.forEach{
                    screen ->
                NavigationBarItem(
                    selected = currentRoute == screen.bRoute,
                    onClick = {

                        navController.navigate(screen.bRoute)


                        title.value = screen.bTitle
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = screen.bIcon),
                            contentDescription = screen.bTitle
                        )
                    },
                    label = {
                    }
                )
            }
        }

    }

    Scaffold(
        bottomBar = {bottomBar()},
        containerColor = Color.White
    ) { innerPadding ->
        Navigation(navController, viewModel, innerPadding, videoData)
    }

}

@Composable
fun Navigation(
    navController: NavController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
    videoData: MainViewModel.VideoState?){

    val homeViewModel : HomeViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()


    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.bRoute,
        modifier = Modifier.padding(innerPadding)) {

        composable(Screen.BottomScreen.Home.bRoute) {
            videoData?.let {
                HomeView(videoData =videoData, homeViewModel)
            }
        }

        composable(Screen.BottomScreen.Search.bRoute) {
            videoData?.let {
                SearchView(videoData, searchViewModel, navController)
            }
        }

        composable(Screen.BottomScreen.Profile.bRoute) {
            ProfileView(navController, profileViewModel)
        }

        composable(Screen.OtherScreens.MatchHistory.oRoute) {
            val matchHistoryViewModel : MatchHistoryViewModel = viewModel()
            MatchHistoryView(matchHistoryViewModel)
        }

        composable(Screen.OtherScreens.Detail.oRoute + "/{videoId}" ,
            arguments = listOf(
                navArgument("videoId"){type = NavType.StringType}
            )) { backStackEntry ->
            DetailView(navController, backStackEntry)
        }

        navigation(
            startDestination = Screen.OtherScreens.Waiting.oRoute,
            route = "match_flow"
        ) {

            composable(Screen.OtherScreens.Waiting.oRoute) {
                val waitingViewModel = it.sharedViewModel<WaitingViewModel>(navController)
                WaitingView(
                    waitingViewModel,
                    navController
                )
            }

            composable(Screen.OtherScreens.Play.oRoute) {
                val waitingViewModel = it.sharedViewModel<WaitingViewModel>(navController)
                PlayView(waitingViewModel, navController)
            }

        }

    }

}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}