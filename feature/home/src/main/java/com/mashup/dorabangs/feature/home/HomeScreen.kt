package com.mashup.dorabangs.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopAppBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        modifier = modifier,
        state = viewModel.collectAsState().value,
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DoraTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "Logo",
            titleAlignment = Alignment.CenterStart,
            actionIcon = R.drawable.ic_plus
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .background(DoraColorTokens.G5)
        )
        DoraChips(
            modifier = Modifier.fillMaxWidth(),
            chipList = state.tapElements
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(state = HomeState(
        tapElements = listOf(
            DoraChipUiModel(
                title = "전체 99+",
                icon = R.drawable.ic_plus,
                isSelected = false
            ),
            DoraChipUiModel(
                title = "하이?",
                isSelected = true
            ),
            DoraChipUiModel(
                title = "바이?",
                isSelected = false
            ),
            DoraChipUiModel(
                title = "바이?",
                isSelected = false
            ),
            DoraChipUiModel(
                title = "바이?",
                icon = R.drawable.ic_plus,
                isSelected = false
            )
        )
    ))
}
