package endava.codebase.android.movieapp.data.di

import endava.codebase.android.movieapp.data.database.FavoriteMovieDAO
import endava.codebase.android.movieapp.data.database.MovieAppDatabase
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<FavoriteMovieDAO> {
        val database = get<MovieAppDatabase>()
        database.favoriteMovieDao()
    }
    single<MovieRepository> {
        MovieRepositoryImpl(
            movieService = get(),
            movieDao = get(),
            bgDispatcher = Dispatchers.IO
        )
    }
}
