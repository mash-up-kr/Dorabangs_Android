package com.mashup.dorabangs.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraBottomNavigation(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = DoraColorTokens.P1,
            contentColor = DoraColorTokens.P1,
            content = content,
        )
    }
}

@Composable
fun RowScope.BottomNavigationItems(
    selected: Boolean,
    label: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        label = {
            Text(
                text = stringResource(label),
                style = DoraTypoTokens.XSMedium,
                color = if (selected) DoraColorTokens.G9 else DoraColorTokens.G4,
            )
        },
        alwaysShowLabel = true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DoraColorTokens.G9,
            unselectedIconColor = DoraColorTokens.G2,
            indicatorColor = Color.Transparent,
        ),
    )
}

@Preview
@Composable
fun PreviewBottomNavigation() {
    val destinations = BottomNavigationDestination.values()
    DoraBottomNavigation {
        destinations.forEach { destination ->
            BottomNavigationItems(
                selected = true,
                onClick = { },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = null,
                    )
                },
                label = destination.routeName,
            )
        }
    }
}
