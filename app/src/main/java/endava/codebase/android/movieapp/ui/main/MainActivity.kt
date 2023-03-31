package endava.codebase.android.movieapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import endava.codebase.android.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(Modifier)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val favoritesViewState = FavoritesMapperImpl().toFavoritesViewState(MoviesMock.getMoviesList())

    /*Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(Modifier)
        },
        bottomBar = {
            BottomBar(Modifier)
        },
    ) { padding ->
        FavoritesScreen(favoritesViewState, Modifier.padding(padding))
    }*/
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tmdb_logo),
                contentDescription = stringResource(R.string.tmdb_logo)
            )
        }
    }
}

@Composable
fun BottomBar(
    // navController : NavController,
    modifier: Modifier = Modifier
) {

    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, stringResource(R.string.home))
            },
            label = { Text(stringResource(R.string.home)) },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
                println("Clicked home, index value ${selectedIndex.value}")
                // navController.navigate()
            }
        )

        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Favorite, stringResource(R.string.favorites))
            },
            label = { Text(stringResource(R.string.favorites)) },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
                println("Clicked favorites, index value ${selectedIndex.value}")
                // navController.navigate()
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        HomeScreen()
    }
}
