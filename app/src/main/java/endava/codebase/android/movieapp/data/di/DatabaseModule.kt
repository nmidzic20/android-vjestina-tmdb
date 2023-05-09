package endava.codebase.android.movieapp.data.di

import androidx.room.Room
import endava.codebase.android.movieapp.data.database.MovieAppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "app_database.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieAppDatabase::class.java,
            APP_DATABASE_NAME,
        ).build()
    }
}
