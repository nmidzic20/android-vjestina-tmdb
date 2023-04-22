package endava.codebase.android.movieapp.ui.home.di

import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.home.HomeViewModel
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            movieRepository = get(),
            homeScreenMapper = get(),
            selectedNewMovieCategory = MovieCategory.NOW_PLAYING,
            selectedTrendingMovieCategory = MovieCategory.POPULAR
        )
    }
    single<HomeScreenMapper> { HomeScreenMapperImpl() }
}
