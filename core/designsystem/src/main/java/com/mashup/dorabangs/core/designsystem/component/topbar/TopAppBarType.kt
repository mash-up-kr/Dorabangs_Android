package com.mashup.dorabangs.core.designsystem.component.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

object DorabangsTopBar : TopAppBarType {
    @Composable
    override fun HomeTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickActonIcon: () -> Unit,
    ) {
        DorabangsTopAppBar(
            modifier = modifier,
            title = title,
            titleAlignment = Alignment.CenterStart,
            actionIcon = actionIcon,
            onClickActonIcon = onClickActonIcon,
        )
    }

    @Composable
    override fun BackNavigationTopBar(
        modifier: Modifier,
        title: String,
        titleAlignment: Alignment,
        onClickBackIcon: () -> Unit,
    ) {
        DorabangsTopAppBar(
            modifier = modifier,
            title = title,
            titleAlignment = titleAlignment,
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
        DorabangsTopAppBar(
            modifier = modifier,
            title = title,
            titleAlignment = Alignment.CenterStart,
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
        DorabangsTopAppBar(
            modifier = modifier,
            title = title,
            titleAlignment = Alignment.Center,
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
        titleAlignment: Alignment,
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
