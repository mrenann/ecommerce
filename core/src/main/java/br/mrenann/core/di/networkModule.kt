package br.mrenann.core.di

import br.mrenann.core.BuildConfig
import br.mrenann.core.data.remote.ParamsInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS = 15L

val networkModule =
    module {

        // Provide ParamsInterceptor
        single { ParamsInterceptor() }

        single {
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
            }
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(get<ParamsInterceptor>()) // Inject ParamsInterceptor
                .addInterceptor(get<HttpLoggingInterceptor>()) // Inject LoggingInterceptor
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
        }

        single { GsonConverterFactory.create() }

        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL) // Use your BASE_URL from BuildConfig
                .client(get<OkHttpClient>()) // Inject OkHttpClient
                .addConverterFactory(get<GsonConverterFactory>()) // Inject GsonConverterFactory
                .build()
                .create(StoreService::class.java)
        }
    }
