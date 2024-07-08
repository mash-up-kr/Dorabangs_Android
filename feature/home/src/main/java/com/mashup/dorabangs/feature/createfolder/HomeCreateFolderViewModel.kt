package com.mashup.dorabangs.feature.createfolder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.CreateFolder
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeCreateFolderViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase,
) : ViewModel(), ContainerHost<HomeCreateFolderState, HomeCreateFolderSideEffect> {
    override val container = container<HomeCreateFolderState, HomeCreateFolderSideEffect>(HomeCreateFolderState())

    fun createFolder(folderName: String) {
        runCatching {
            viewModelScope.doraLaunch {
                val folderData = CreateFolder(names = listOf(folderName))
                createFolderUseCase(folderData)
            }
        }.onSuccess {
            intent {
                postSideEffect(HomeCreateFolderSideEffect.NavigateToHome)
            }
        }.onFailure { throwable ->
            // TODO - 에러메세지 넘기기
            setTextHelperEnable(false)
        }
    }

    fun setTextHelperEnable(isEnable: Boolean) = intent {
        reduce { state.copy(helperEnable = isEnable) }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(folderName = folderName)
        }
    }
}
