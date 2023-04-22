package endava.codebase.android.movieapp.ui.moviedetails.di

import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewModel
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailsModule = module {
    viewModel { params ->
        MovieDetailsViewModel(
            movieRepository = get(),
            movieDetailsMapper = get(),
            movieId = 1 // params.get()
        )
    }
    single<MovieDetailsMapper> { MovieDetailsMapperImpl() }
}
