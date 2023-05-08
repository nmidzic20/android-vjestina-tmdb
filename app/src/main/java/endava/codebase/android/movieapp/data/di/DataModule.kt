package endava.codebase.android.movieapp.data.di

import endava.codebase.android.movieapp.data.repository.FakeMovieRepository
import endava.codebase.android.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        FakeMovieRepository(ioDispatcher = Dispatchers.IO)
    }
}
