package endava.codebase.android.movieapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import endava.codebase.android.movieapp.navigation.MovieDetailsDestination
import endava.codebase.android.movieapp.navigation.NavigationItem
import endava.codebase.android.movieapp.ui.favorites.FavoritesRoute
import endava.codebase.android.movieapp.ui.favorites.FavoritesViewModel
import endava.codebase.android.movieapp.ui.favorites.HomeRoute
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsRoute
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewModel
import endava.codebase.android.movieapp.ui.theme.spacing
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.koinInject
//import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

//import org.koin.core.parameter.parametersOf
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import org.koin.androidx.compose.viewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                MovieDetailsDestination.route -> {
                    false
                }
                else -> {
                    true
                }
            }
        }
    }
    val showBackIcon = !showBottomBar

    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(onBackClick = navController::popBackStack, Modifier.padding(MaterialTheme.spacing.small))
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeRoute(
                        onNavigateToMovieDetails = { movieId ->
                            val movieRoute = MovieDetailsDestination.createNavigationRoute(movieId)
                            navController.navigate(movieRoute)
                        },
                        viewModel = getViewModel()
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToMovieDetails = { movieId ->
                            val movieRoute = MovieDetailsDestination.createNavigationRoute(movieId)
                            navController.navigate(movieRoute)
                        },
                        viewModel = getViewModel<FavoritesViewModel>()
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    val selectedMovieId = it.arguments?.getInt(MOVIE_ID_KEY)
                    val movieDetailsViewModel: MovieDetailsViewModel = getViewModel() // by viewModel { parametersOf(selectedMovieId) }
                    println("Selected movie ${it.arguments?.getInt(MOVIE_ID_KEY)}")
                    MovieDetailsRoute(
                        viewModel = movieDetailsViewModel
                    )
                }
            }
        }
    }
}
@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    TopAppBar(modifier = Modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (navigationIcon != null) {
                navigationIcon()
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = stringResource(R.string.tmdb_logo),
                )
            }
        }
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        stringResource(R.string.back),
        modifier = modifier.clickable { onBackClick() }
    )
}
@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            val iconResource = if (selected) destination.selectedIconId else destination.unselectedIconId

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(iconResource),
                        contentDescription = stringResource(id = destination.labelId)
                    )
                },
                label = { Text(stringResource(destination.labelId)) },
                selected = selected,
                onClick = {
                    onNavigateToDestination(destination)
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
