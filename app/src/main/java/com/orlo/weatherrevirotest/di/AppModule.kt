package com.orlo.weatherrevirotest.di

import android.app.Application
import androidx.room.Room
import com.orlo.weatherrevirotest.MyApplication
import com.orlo.weatherrevirotest.data.local.WeatherDatabase
import com.orlo.weatherrevirotest.data.remote.WeatherApi
import com.orlo.weatherrevirotest.util.constants.Const.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDB(application: Application): WeatherDatabase {
        return Room.databaseBuilder(
            application,
            WeatherDatabase::class.java,
            "weather.db"
        )
            .build()
    }

}