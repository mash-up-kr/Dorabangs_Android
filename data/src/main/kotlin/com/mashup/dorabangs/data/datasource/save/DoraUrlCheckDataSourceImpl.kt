package com.mashup.dorabangs.data.datasource.save

import com.mashup.dorabangs.data.network.save.DoraUrlService
import javax.inject.Inject

class DoraUrlCheckDataSourceImpl @Inject constructor(
    private val service: DoraUrlService,
) : DoraUrlCheckDataSource {
    override suspend fun checkDataSource(urlLink: String): DoraUrlCheckResponse {
        return service.checkUrl(urlLink)
    }
}
