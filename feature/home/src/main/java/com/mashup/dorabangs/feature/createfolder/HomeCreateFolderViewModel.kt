package com.mashup.dorabangs.feature.createfolder

import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import javax.inject.Inject

class HomeCreateFolderViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase
): ViewModel() {
}