package com.mashup.dorabangs.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.usecase.user.GetUserAccessTokenUseCase
import com.mashup.dorabangs.domain.usecase.user.RegisterUserUseCase
import com.mashup.dorabangs.domain.usecase.user.SetUserAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserAccessTokenUseCase: GetUserAccessTokenUseCase,
    private val setUserAccessTokenUseCase: SetUserAccessTokenUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    fun checkUserToken(userId: String) {
        viewModelScope.doraLaunch {
            val splashTimerJob = launch {
                delay(3000)
                splashShowFlow.value = false // Todo :: 시간 초 지나서도 스플래시가 안꺼지면, 실패 처리 넣어야함
            }.also {
                it.start()
            }

            val userAccessToken = getUserAccessTokenUseCase().first()
                .ifEmpty {
                    registerUserUseCase(DeviceToken(userId)).also {
                        setUserAccessTokenUseCase(it)
                    }
                }

            val hasUserAccessToken = userAccessToken.isNotEmpty()
            if (hasUserAccessToken) {
                splashTimerJob.cancel()
                splashShowFlow.value = false
            }
        }
    }
}
