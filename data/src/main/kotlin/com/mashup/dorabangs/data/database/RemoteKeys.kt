package com.mashup.dorabangs.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val postId: String,
    val prevKey: Int? = null,
    val nextKey: Int? = null,
)
