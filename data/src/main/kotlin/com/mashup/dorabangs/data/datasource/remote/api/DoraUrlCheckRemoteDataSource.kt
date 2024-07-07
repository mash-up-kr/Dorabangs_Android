package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckResponse

interface DoraUrlCheckRemoteDataSource {
    suspend fun checkDataSource(urlLink: String): DoraUrlCheckResponse
}
