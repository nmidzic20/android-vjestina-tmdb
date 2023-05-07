package endava.codebase.android.movieapp.data.di

import android.util.Log
import androidx.room.Room
import endava.codebase.android.movieapp.data.database.MovieAppDatabase
import endava.codebase.android.movieapp.data.network.MovieService
import endava.codebase.android.movieapp.data.network.MovieServiceImpl
import endava.codebase.android.movieapp.data.repository.FakeMovieRepository
import endava.codebase.android.movieapp.data.repository.MovieRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
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

val networkModule = module {
    single<MovieRepository> {
        FakeMovieRepository(ioDispatcher = Dispatchers.IO)
    }
    single<MovieService> { MovieServiceImpl(client = get()) }
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message : String) {
                        Log.d("HTTP", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
