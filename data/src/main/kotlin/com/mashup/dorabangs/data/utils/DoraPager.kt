package com.mashup.dorabangs.data.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mashup.dorabangs.data.pagingsource.DoraPagingSource
import com.mashup.dorabangs.domain.model.PageData

fun <T : Any> doraPager(
    needFetchUpdate: Boolean = false,
    cachedList: HashMap<String, PageData<List<T>>> = HashMap(),
    pageSize: Int = PAGING_SIZE,
    cacheKey: String = "",
    apiExecutor: suspend (Int) -> PageData<List<T>>,
    totalCount: (Int) -> Unit = {},
    cachingData: (PageData<List<T>>, Int) -> Unit = { _, _ -> },
): Pager<Int, T> = Pager(
    config = PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = false,
        initialLoadSize = PAGING_SIZE,
    ),
    pagingSourceFactory = {
        DoraPagingSource(
            needFetchUpdate = needFetchUpdate,
            cachedList = cachedList,
            cacheKey = cacheKey,
            cachingData = cachingData,
            apiExecutor = apiExecutor,
            totalCount = totalCount,
        )
    },
)

const val PAGING_SIZE = 10
