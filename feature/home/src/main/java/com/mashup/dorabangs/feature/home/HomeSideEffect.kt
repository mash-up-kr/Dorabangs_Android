package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel

sealed class HomeSideEffect {
    data class ShowSnackBar(val copiedText: String) : HomeSideEffect()
    object HideSnackBar : HomeSideEffect()
    object NavigateToCreateFolder : HomeSideEffect()
    object NavigateToHome : HomeSideEffect()
    data class SaveLink(val folderId: String, val urlLink: String) : HomeSideEffect()
    object NavigateHomeAfterSaveLink : HomeSideEffect()
    data class NavigateSelectLinkFromService(val urlLink: String) : HomeSideEffect()
    object ResetPostList : HomeSideEffect()
    data class UpdatePost(val post: FeedCardUiModel) : HomeSideEffect()
}
