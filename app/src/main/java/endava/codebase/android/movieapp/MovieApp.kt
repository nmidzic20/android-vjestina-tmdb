package endava.codebase.android.movieapp

import android.app.Application
import android.util.Log
import endava.codebase.android.movieapp.data.di.dataModule
import endava.codebase.android.movieapp.ui.favorites.di.favoritesModule
import endava.codebase.android.movieapp.ui.home.di.homeCategoryModule
import endava.codebase.android.movieapp.ui.moviedetails.di.movieDetailsModule
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                dataModule,
                favoritesModule,
                movieDetailsModule,
                homeCategoryModule
            )
        }

        Log.d("MovieApp", "App started")
    }
}
