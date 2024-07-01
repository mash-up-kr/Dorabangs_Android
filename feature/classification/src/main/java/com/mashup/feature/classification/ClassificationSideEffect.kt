package com.mashup.feature.classification

sealed class ClassificationSideEffect {
    data class OnClickDeleteCard(val idx: Int) : ClassificationSideEffect()
    object OnClickAllItemMove : ClassificationSideEffect()
    data class OnClickSelectItemMove(val idx: Int) : ClassificationSideEffect()
}
