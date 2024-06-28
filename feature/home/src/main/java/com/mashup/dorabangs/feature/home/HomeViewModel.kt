package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        intent {
            reduce {
                HomeState(tapElements = listOf(
                    DoraChipUiModel(
                        title = "전체 99+",
                        icon = R.drawable.ic_plus,
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "하이?",
                        isSelected = true
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        icon = R.drawable.ic_plus,
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "전체 99+",
                        icon = R.drawable.ic_plus,
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "하이?",
                        isSelected = true
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        isSelected = false
                    ),
                    DoraChipUiModel(
                        title = "바이?",
                        icon = R.drawable.ic_plus,
                        isSelected = false
                    )
                ))
            }
        }
    }
}
