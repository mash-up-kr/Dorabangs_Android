package com.mashup.dorabangs.core.designsystem.component.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TopBarColorTokens

@Composable
fun DoraTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    isTitleCenter: Boolean = false,
    isEnableBackNavigation: Boolean = false,
    @DrawableRes actionIcon: Int? = null,
    onClickBackIcon: () -> Unit = {},
    onClickActonIcon: () -> Unit = {},
) {
    val isHomeAppBar = isTitleCenter && !isEnableBackNavigation
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = DoraColorTokens.White),
    ) {
        val horizontalArrangement = if (isTitleCenter) Arrangement.Center else Arrangement.Start
        if (isTitleCenter && isEnableBackNavigation) {
            Icon(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 20.dp)
                    .clickable { onClickBackIcon() },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "navigation",
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 16.dp),
                text = title,
                color = TopBarColorTokens.OnContainerColor,
                style = DoraTypoTokens.base1Bold,
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement,
            ) {
                if (isEnableBackNavigation) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clickable { onClickBackIcon() },
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "navigation",
                    )
                }
                Text(
                    modifier = Modifier.padding(start = if (isEnableBackNavigation) 16.dp else 20.dp),
                    text = title,
                    color = if (isHomeAppBar) TopBarColorTokens.OnContainerColorHome else TopBarColorTokens.OnContainerColor,
                    style = if (isHomeAppBar) DoraTypoTokens.Subtitle1Bold else DoraTypoTokens.base1Bold,
                )
            }
        }
        actionIcon?.let { icon ->
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .clickable { onClickActonIcon() },
                painter = painterResource(id = icon),
                contentDescription = "action",
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeTopAppBar() {
    DoraTopBar.HomeTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
        actionIcon = R.drawable.ic_plus,
    ) {}
}

@Preview
@Composable
fun PreviewBackNavigationTopBar() {
    DoraTopBar.BackNavigationTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
        isTitleCenter = true,
    ) {}
}

@Preview
@Composable
fun PreviewBackWithActionIconTopBar() {
    DoraTopBar.BackWithActionIconTopBar(
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
    DoraTopBar.TitleTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Dorabangs",
    )
}
