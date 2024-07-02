package com.mashup.dorabangs.feature.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

    fun onClickKeyword(index: Int) = intent {
        reduce {
            if (state.selectedIndex.contains(index)) {
                state.copy(selectedIndex = state.selectedIndex.minus(index))
            } else {
                state.copy(selectedIndex = state.selectedIndex.plus(index))
            }
        }
    }

    fun onClickOkButton() = intent {
        postSideEffect(OnBoardingSideEffect.NavigateToHome)
    }

    init {
        intent {
            reduce {
                state.copy(
                    keywords = listOf(
                        "테스트",
                        "입니다",
                        "ㅎㅎㅎㅎㅎㅎㅎ",
                        "올라",
                        "떡볶이",
                        "후참잘",
                        "별로더라고???",
                        "ㅋㅋㅋㅋㅋㅋ",
                        "아",
                        "퇴근했는데",
                        "퇴근못함",
                        "길게 한번 가자고고고고곡고고",
                        "빨래",
                        "널어놨는데",
                        "장마철이라",
                        "냄새가",
                        "꿉꿉해서",
                        "다시",
                        "빨아야함",
                        "이거",
                        "실화냐!!!!!!"
                    )
                )
            }
        }
    }
}