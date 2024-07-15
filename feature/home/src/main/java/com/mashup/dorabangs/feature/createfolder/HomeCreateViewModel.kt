package com.mashup.dorabangs.feature.createfolder

import com.mashup.dorabangs.core.base.BaseViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

class HomeCreateViewModel : BaseViewModel(), ContainerHost<HomeCreateState, HomeCreateSideEffect> {
    override val container: Container<HomeCreateState, HomeCreateSideEffect> = container(HomeCreateState())

    override fun handleErrors(error: Throwable) {
        // 구분하는 기준은 메세지가 아니여도 됨
        when (error.message) {
            "DoraCustomErrorExample" -> "뿡냥구링" // Todo:: 처리

            // 공통 처리
            else -> intent {
                postSideEffect(HomeCreateSideEffect.ShowSnackBar(error.message.orEmpty()))
            }
        }
    }
}