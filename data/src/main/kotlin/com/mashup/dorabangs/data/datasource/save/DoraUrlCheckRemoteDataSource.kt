package com.mashup.dorabangs.data.datasource.save

interface DoraUrlCheckRemoteDataSource {
    suspend fun checkDataSource(urlLink: String): DoraUrlCheckResponse
}
