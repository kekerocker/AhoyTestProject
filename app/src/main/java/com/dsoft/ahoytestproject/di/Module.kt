package com.dsoft.ahoytestproject.di

import android.content.Context
import androidx.room.Room
import com.dsoft.core.BuildConfig
import com.dsoft.core.receiver.NotificationReceiver
import com.dsoft.core.service.NotificationService
import com.dsoft.core.util.NotificationUtil
import com.dsoft.data.api.WeatherApiRequest
import com.dsoft.data.db.AhoyDB
import com.dsoft.data.db.FavouritesDao
import com.dsoft.data.repository.WeatherRepositoryImpl
import com.dsoft.domain.repository.WeatherRepository
import com.dsoft.domain.usecase.AddFavouriteCityToDBUseCase
import com.dsoft.domain.usecase.GetAllFavouriteCityUseCase
import com.dsoft.domain.usecase.GetWeatherByCityNameUseCase
import com.dsoft.domain.usecase.GetWeatherByUserLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApiRequest {
        return retrofit.create(WeatherApiRequest::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabaseLogger(
        apiRequest: WeatherApiRequest,
        favouritesDao: FavouritesDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiRequest, favouritesDao)
    }

    @Singleton
    @Provides
    fun provideGetWeatherByUserLocationUseCase(repository: WeatherRepository): GetWeatherByUserLocationUseCase {
        return GetWeatherByUserLocationUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllFavouriteCityUseCase(repository: WeatherRepository): GetAllFavouriteCityUseCase {
        return GetAllFavouriteCityUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideAddFavouriteCity(repository: WeatherRepository): AddFavouriteCityToDBUseCase {
        return AddFavouriteCityToDBUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetWeatherByCityNameUseCase(repository: WeatherRepository): GetWeatherByCityNameUseCase {
        return GetWeatherByCityNameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AhoyDB::class.java,
            "ahoy_db"
        ).build()

    @Singleton
    @Provides
    fun provideFavouritesDao(db: AhoyDB) = db.favouritesDao()

    @Singleton
    @Provides
    fun provideBroadcastReceiver(): NotificationReceiver = NotificationReceiver()

    @Singleton
    @Provides
    fun provideNotificationService(): NotificationService = NotificationService()

    @Singleton
    @Provides
    fun provideNotificationUtil(): NotificationUtil = NotificationUtil()
}