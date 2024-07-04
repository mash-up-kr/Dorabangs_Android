package com.mashup.dorabangs.data.repository.save

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckDataSource
import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel
import javax.inject.Inject

class DoraUrlCheckRepositoryImpl @Inject constructor(
    private val remote: DoraUrlCheckDataSource,
) : DoraUrlCheckRepository {
    override suspend fun checkUrl(urlLink: String): DoraSaveLinkDataModel {
        return remote.checkDataSource(urlLink).toDataModel()
    }
}