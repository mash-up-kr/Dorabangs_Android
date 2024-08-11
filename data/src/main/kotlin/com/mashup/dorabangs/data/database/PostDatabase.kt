package com.mashup.dorabangs.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mashup.dorabangs.data.utils.ListTypeConverters

@Database(entities = [LocalPostItemEntity::class, RemoteKeys::class], version = 1)
@TypeConverters(ListTypeConverters::class)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {

        private var instance: PostDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    PostDatabase::class.java,
                    "post.db",
                ).build()
            }
    }
}
