package endava.codebase.android.movieapp.data.di

import android.util.Log
import endava.codebase.android.movieapp.data.network.MovieService
import endava.codebase.android.movieapp.data.network.MovieServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
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
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                })
            }
        }
    }
}