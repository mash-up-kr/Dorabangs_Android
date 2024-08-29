package com.mashup.dorabangs.core.designsystem.component.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

object DoraTopBar : TopAppBarType {
    @Composable
    override fun HomeTopBar(
        modifier: Modifier,
        title: String,
        actionIcon: Int,
        onClickActonIcon: () -> Unit,
    ) {
        Row(
            modifier = modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_top_app_bar),
                contentDescription = "",
            )
            Icon(
                modifier = Modifier.clickable { onClickActonIcon.invoke() },
                painter = painterResource(id = R.drawable.ic_add_link),
                contentDescription = "",
            )
        }
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
        isTitleCenter: Boolean,
        onClickBackIcon: () -> Unit,
        onClickActonIcon: () -> Unit,
    ) {
        DoraTopAppBar(
            modifier = modifier.background(DoraColorTokens.White),
            title = title,
            isTitleCenter = isTitleCenter,
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
        isTitleCenter: Boolean,
        onClickBackIcon: () -> Unit,
        onClickActonIcon: () -> Unit,
    )

    @Composable
    fun TitleTopAppBar(
        modifier: Modifier,
        title: String,
    )
}
