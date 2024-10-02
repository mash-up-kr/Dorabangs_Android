package com.mashup.dorabangs.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.usecase.user.GetIsFirstEntryUseCase
import com.mashup.dorabangs.domain.usecase.user.GetUserAccessTokenUseCase
import com.mashup.dorabangs.domain.usecase.user.RegisterUserUseCase
import com.mashup.dorabangs.domain.usecase.user.SetUserAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserAccessTokenUseCase: GetUserAccessTokenUseCase,
    private val setUserAccessTokenUseCase: SetUserAccessTokenUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getIsFirstEntryUseCase: GetIsFirstEntryUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    private val _firstEntryScreen = MutableStateFlow(FirstEntryScreen.Splash)
    val firstEntryScreen: StateFlow<FirstEntryScreen> = _firstEntryScreen.asStateFlow()

    fun checkUserToken(userId: String) {
        viewModelScope.doraLaunch {
            val userAccessToken = getUserAccessTokenUseCase().firstOrNull()
                ?.ifEmpty {
                    val token = registerUserUseCase(DeviceToken(userId))
                    setUserAccessTokenUseCase(token)
                    token
                }.orEmpty()

            if (userAccessToken.isNotEmpty()) {
                delay(SPLASH_SCREEN_TIME)
                splashShowFlow.value = false
                _firstEntryScreen.value = if (getIsFirstEntryUseCase().firstOrNull() != false) FirstEntryScreen.Onboarding else FirstEntryScreen.Home
            } else {
                // Todo :: 유저 토큰 가져오기 실패에 대한 처리 해줘야함 (Like 토스트 메시지)
            }
        }
    }

    companion object {
        private const val SPLASH_SCREEN_TIME = 3000L
    }
}

enum class FirstEntryScreen {
    Splash, Onboarding, Home, SaveLink
}
