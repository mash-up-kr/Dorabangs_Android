package com.mashup.dorabangs.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    /**
     * 전체 post 목록 PagingSource로 가져오기
     */
    @Query("SELECT * FROM localPostItemEntity")
    suspend fun getPostByPage(): List<LocalPostItemEntity>

    @Query("SELECT * FROM localPostItemEntity ORDER BY createdAt DESC LIMIT :limit")
    suspend fun getRecentPosts(limit: Int = 10): List<LocalPostItemEntity>

    @Query(
        """
    SELECT * FROM localPostItemEntity
    WHERE (:isRead = 1) OR (:isRead = 0 AND readAt = '' OR readAt IS NULL)
    ORDER BY createdAt ASC """,
    )
    fun getAllPostsOrderedByAsc(isRead: Boolean?): PagingSource<Int, LocalPostItemEntity>

    @Query(
        """
    SELECT * FROM localPostItemEntity
    WHERE (:isRead = 1) OR (:isRead = 0 AND readAt = '' OR readAt IS NULL)
    ORDER BY createdAt DESC """,
    )
    fun getAllPostsOrderedByDesc(isRead: Boolean?): PagingSource<Int, LocalPostItemEntity>

    /**
     * 전체 목록 삽입
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<LocalPostItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: LocalPostItemEntity)

    /**
     * Post 아이템 삭제
     */
    @Query("DELETE FROM localPostItemEntity where id = :postId")
    suspend fun deletePostItem(postId: String)

    /**
     * refresh일시 전체 삭제
     */
    @Query("DELETE FROM localPostItemEntity")
    suspend fun clearAllPostItem()

    /**
     * 북마크 update
     */
    @Query("UPDATE localPostItemEntity SET isFavorite = :isFavorite WHERE id = :postId")
    suspend fun updateFavoriteStatus(postId: String, isFavorite: Boolean)
}
