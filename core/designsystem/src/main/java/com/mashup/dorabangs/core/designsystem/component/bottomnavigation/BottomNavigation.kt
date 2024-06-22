package com.mashup.dorabangs.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
    ) {
        NavigationBar(
            modifier = modifier,
            contentColor = Color.White, // designsystem 후 수정 필요
            content = content,
        )
    }
}

@Composable
fun RowScope.BottomNavigationItems(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    contentColor: Color,
    selectedColor: Color,
    unSelectedColor: Color,
    label: Int,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        label = {
            Text(stringResource(label))
        },
        alwaysShowLabel = true,
        colors =
            NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unSelectedColor,
                indicatorColor = contentColor,
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
                contentColor = Color.Transparent,
                selectedColor = Color.Black,
                unSelectedColor = Color.Gray,
            )
        }
    }
}
