package com.mashup.dorabangs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mashup.dorabangs.data.utils.DoraException
import com.mashup.dorabangs.data.utils.doraConvertKey
import com.mashup.dorabangs.domain.model.PageData
import retrofit2.HttpException

class DoraPagingSource<T : Any> (
    private val needFetchUpdate: Boolean,
    private val cachedList: HashMap<String, PageData<List<T>>>,
    private val cacheKey: String,
    private val cachingData: (PageData<List<T>>, Int) -> Unit,
    private val apiExecutor: suspend (Int) -> PageData<List<T>>,
    private val totalCount: (Int) -> Unit,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: INITIAL_PAGE
        val loadSize = params.loadSize

        val cachedData = cachedList[doraConvertKey(page, cacheKey)]
        return try {
            if (cachedData != null && needFetchUpdate.not()) {
                val isLastPage = (!cachedData.pagingInfo.hasNext) || (cachedData.pagingInfo.total == 0)
                totalCount(cachedData.pagingInfo.total)
                LoadResult.Page(
                    data = cachedData.data,
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (isLastPage) null else page + 1,
                )
            } else {
                val result = apiExecutor(page)
                val isLastPage = (!result.pagingInfo.hasNext) || (result.pagingInfo.total == 0)
                totalCount(result.pagingInfo.total)
                cachingData(result, page)

                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (isLastPage) null else page + 1,
                )
            }
        } catch (e: HttpException) {
            LoadResult.Error(DoraException(message = e.message()))
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}
