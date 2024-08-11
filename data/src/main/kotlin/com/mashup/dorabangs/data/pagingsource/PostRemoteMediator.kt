package com.mashup.dorabangs.data.pagingsource

import android.content.ContentValues
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mashup.dorabangs.data.database.LocalPostItemEntity
import com.mashup.dorabangs.data.database.PostDatabase
import com.mashup.dorabangs.data.database.RemoteKeys
import com.mashup.dorabangs.data.database.toLocalEntity
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Post
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator<T: Any> (
    private val database: PostDatabase,
    private val needFetchUpdate: Boolean,
    private val apiExecutor: suspend (Int) -> PageData<List<T>>,
    private val getRemoteKey: (T) -> RemoteKeys,
    private val toLocalEntity: (T) -> LocalPostItemEntity,
    private val totalCount: (Int) -> Unit,
) : RemoteMediator<Int, LocalPostItemEntity>() {

    private val STARTING_PAGE_INDEX = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalPostItemEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return try {
            if(needFetchUpdate.not()) {
                val cachedPosts = database.postDao().getPostByPage()
                if (cachedPosts.isNotEmpty()) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }
            val apiResponse = apiExecutor(page)
            val posts = apiResponse.data
            val endOfPaginationReached = posts.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.postDao().clearAllPostItem()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = posts.map { item ->
                    val remoteKey = getRemoteKey(item)
                    remoteKey.copy(prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.postDao().insertAll(posts.map { toLocalEntity(it) })
            }
            totalCount(apiResponse.pagingInfo.total)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    /**
     * Append : 현재 로드된 데이터 세트의 끝 부분에서 데이터를 로드해야 하는 경우
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalPostItemEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { post ->
                database.remoteKeysDao().remoteKeysRepoId(post.id)
            }
    }

    /**
     * Prepend : 현재 로드된 데이터 세트의 시작 부분에서 데이터를 로드해야 하는 경우
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocalPostItemEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { post ->
                database.remoteKeysDao().remoteKeysRepoId(post.id)
            }
    }

    /**
     * Refresh : 데이터 처음 호출 및 새로고침
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalPostItemEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { postId ->
                database.remoteKeysDao().remoteKeysRepoId(postId)
            }
        }
    }

}