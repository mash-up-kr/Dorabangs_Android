package com.mashup.dorabangs.core.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

object DoraTopBar : TopAppBarType {
    @Composable
    override fun HomeTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickActonIcon: () -> Unit,
    ) {
        DoraTopAppBar(
            modifier = modifier,
            title = title,
            isTitleCenter = false,
            actionIcon = actionIcon,
            onClickActonIcon = onClickActonIcon,
        )
    }

    @Composable
    override fun BackNavigationTopBar(
        modifier: Modifier,
        title: String,
        isTitleCenter: Boolean,
        onClickBackIcon: () -> Unit,
    ) {
        DoraTopAppBar(
            modifier = modifier.background(DoraColorTokens.White),
            title = title,
            isTitleCenter = isTitleCenter,
            isEnableBackNavigation = true,
            onClickBackIcon = onClickBackIcon,
        )
    }

    @Composable
    override fun BackWithActionIconTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickBackIcon: () -> Unit,
        onClickActonIcon: () -> Unit,
    ) {
        DoraTopAppBar(
            modifier = modifier.background(DoraColorTokens.White),
            title = title,
            isTitleCenter = false,
            isEnableBackNavigation = true,
            actionIcon = actionIcon,
            onClickBackIcon = onClickBackIcon,
            onClickActonIcon = onClickActonIcon,
        )
    }

    @Composable
    override fun TitleTopAppBar(
        modifier: Modifier,
        title: String,
    ) {
        DoraTopAppBar(
            modifier = modifier.background(DoraColorTokens.White),
            title = title,
            isTitleCenter = true,
        )
    }
}

sealed interface TopAppBarType {
    @Composable
    fun HomeTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickActonIcon: () -> Unit,
    )

    @Composable
    fun BackNavigationTopBar(
        modifier: Modifier,
        title: String,
        isTitleCenter: Boolean,
        onClickBackIcon: () -> Unit,
    )

    @Composable
    fun BackWithActionIconTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickBackIcon: () -> Unit,
        onClickActonIcon: () -> Unit,
    )

    @Composable
    fun TitleTopAppBar(
        modifier: Modifier,
        title: String,
    )
}
