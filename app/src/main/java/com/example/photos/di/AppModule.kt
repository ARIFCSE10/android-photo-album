package com.example.photos.di

import android.app.Application
import androidx.room.Room
import com.example.photos.common.Constants
import com.example.photos.data.local.AlbumCacheDB
import com.example.photos.data.remote.PlaceHolderApi
import com.example.photos.data.repository.AlbumCacheRepositoryImpl
import com.example.photos.data.repository.AlbumRepositoryImpl
import com.example.photos.domain.repository.AlbumCacheRepository
import com.example.photos.domain.repository.AlbumRepository
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
    fun provideAlbumRepository(api: PlaceHolderApi): AlbumRepository {
        return AlbumRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesAlbumCacheDB(app: Application): AlbumCacheDB {
        return Room.databaseBuilder(app, AlbumCacheDB::class.java, AlbumCacheDB.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumCacheRepository(albumCacheDB: AlbumCacheDB): AlbumCacheRepository {
        return AlbumCacheRepositoryImpl(albumCacheDB.albumCacheDao)
    }
}