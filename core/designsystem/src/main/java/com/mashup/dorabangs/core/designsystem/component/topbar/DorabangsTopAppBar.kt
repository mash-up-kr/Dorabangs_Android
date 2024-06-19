package com.mashup.dorabangs.core.designsystem.component.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DorabangsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    titleAlignment: Alignment = Alignment.Center,
    isEnableBackNavigation: Boolean = false,
    @DrawableRes actionIcon: Int? = null,
    onClickBackIcon: () -> Unit = {},
    onClickActonIcon: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = titleAlignment,
            ) {
                val startPadding =
                    if (titleAlignment == Alignment.CenterStart && isEnableBackNavigation) 16.dp else 0.dp
                Text(
                    modifier = Modifier.padding(start = startPadding),
                    text = title,
                )
            }
        },
        actions = {
            if (actionIcon != null) {
                Icon(
                    modifier =
                    Modifier
                        .padding(end = 20.dp)
                        .clickable { onClickActonIcon() },
                    painter = painterResource(id = actionIcon),
                    contentDescription = null,
                )
            }
        },
        navigationIcon = {
            if (isEnableBackNavigation) {
                Icon(
                    modifier =
                    Modifier
                        .padding(start = 20.dp)
                        .clickable { onClickBackIcon() },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview
@Composable
fun PreviewHomeTopAppBar() {
    DorabangsTopBar.HomeTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
        actionIcon = R.drawable.ic_plus,
    ) {}
}

@Preview
@Composable
fun PreviewBackNavigationTopBar() {
    DorabangsTopBar.BackNavigationTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
        titleAlignment = Alignment.CenterStart,
    ) {}
}

@Preview
@Composable
fun PreviewBackWithActionIconTopBar() {
    DorabangsTopBar.BackWithActionIconTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
        actionIcon = R.drawable.ic_plus,
        onClickBackIcon = {},
        onClickActonIcon = {},
    )
}

@Preview
@Composable
fun PreviewTitleTopAppBar() {
    DorabangsTopBar.TitleTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
    )
}
