package com.example.photos.di

import android.app.Application
import androidx.room.Room
import com.example.photos.common.Constants
import com.example.photos.data.local.LocalCacheDB
import com.example.photos.data.remote.PlaceHolderApi
import com.example.photos.data.repository.LocalRepositoryImpl
import com.example.photos.data.repository.RemoteRepositoryImpl
import com.example.photos.domain.repository.LocalRepository
import com.example.photos.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlaceHolderApi(): PlaceHolderApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PlaceHolderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(api: PlaceHolderApi): RemoteRepository {
        return RemoteRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesLocalCacheDB(app: Application): LocalCacheDB {
        return Room.databaseBuilder(app, LocalCacheDB::class.java, LocalCacheDB.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesLocalRepository(localCacheDB: LocalCacheDB): LocalRepository {
        return LocalRepositoryImpl(localCacheDB.localRepositoryDao)
    }
}