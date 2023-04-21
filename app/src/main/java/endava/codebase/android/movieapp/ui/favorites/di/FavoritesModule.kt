package endava.codebase.android.movieapp.ui.favorites.di

import endava.codebase.android.movieapp.ui.favorites.FavoritesViewModel
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            movieRepository = get(),
            favoritesScreenMapper = get()
        )
    }
    single<FavoritesMapper> { FavoritesMapperImpl() }
}
