package com.mashup.dorabangs.data.repository.save

import com.mashup.dorabangs.data.datasource.remote.api.DoraUrlCheckRemoteDataSource
import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel
import javax.inject.Inject

class DoraUrlCheckRepositoryImpl @Inject constructor(
    private val remote: DoraUrlCheckRemoteDataSource,
) : DoraUrlCheckRepository {
    override suspend fun checkUrl(urlLink: String): DoraSaveLinkDataModel {
        return remote.checkDataSource(urlLink).toDataModel()
    }
}
