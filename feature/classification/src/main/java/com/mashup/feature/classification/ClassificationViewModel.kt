package com.mashup.feature.classification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor() : ViewModel(), ContainerHost<ClassificationState, ClassificationSideEffect> {
    override val container = container<ClassificationState, ClassificationSideEffect>(ClassificationState())

    fun changeCategory() = intent {}

    fun moveAllItems() = intent {
        postSideEffect(ClassificationSideEffect.OnClickAllItemMove)
    }

    fun moveSelectedItem(idx: Int) = intent {
        postSideEffect(ClassificationSideEffect.OnClickSelectItemMove(idx = idx))
    }

    fun deleteSelectedItem(idx: Int) = intent {
        postSideEffect(ClassificationSideEffect.OnClickDeleteCard(idx = idx))
    }
}
