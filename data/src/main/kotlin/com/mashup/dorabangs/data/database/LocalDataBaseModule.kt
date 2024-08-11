package com.mashup.dorabangs.data.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Singleton
    @Provides
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase {
        return PostDatabase.getInstance(context)
    }

    @Provides
    fun providePostDao(postDatabase: PostDatabase): PostDao {
        return postDatabase.postDao()
    }
}