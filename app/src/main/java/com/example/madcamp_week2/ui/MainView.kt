package com.example.madcamp_week2.ui

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.madcamp_week2.MainViewModel


@Composable
fun MainView(){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val viewModel :MainViewModel = viewModel()
    val videoData by viewModel.videoData

    // Load mock data for test
    LaunchedEffect(Unit) {
        viewModel.loadVideoData("59ek8wuhpgk") // Mock YouTube video ID
    }
    videoData?.let { data ->
        HomeView(videoData = data) // videoData의 값을 HomeView에 전달
    }

    val currentScreen = remember { viewModel.currentScreen.value}
    val title = remember { mutableStateOf(currentScreen.title) }

    val bottomBar: @Composable () -> Unit = {
        NavigationBar(
            Modifier.wrapContentSize(),
            containerColor = Color.White,
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
                        Text(text = screen.bTitle, fontSize = 10.sp)
                    }
                )
            }
        }

    }

    Scaffold(
        bottomBar = {bottomBar()}
    ) { innerPadding ->
        Navigation(navController, viewModel, innerPadding, videoData)
    }

}

@Composable
fun Navigation(
    navController: NavController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
    videoData: VideoData?){
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.bRoute,
        modifier = Modifier.padding(innerPadding)) {
        composable(Screen.BottomScreen.Home.bRoute) {
            videoData?.let { data ->
                HomeView(videoData = data)
            }
        }

        composable(Screen.BottomScreen.Search.bRoute) {
            SearchView()
        }

        composable(Screen.BottomScreen.Profile.bRoute) {
            ProfileView()
        }
    }

}