package com.mashup.dorabangs.data.datasource.save

interface DoraUrlCheckDataSource {
    suspend fun checkDataSource(urlLink: String): DoraUrlCheckResponse
}