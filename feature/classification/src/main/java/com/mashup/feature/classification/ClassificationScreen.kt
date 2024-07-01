package com.mashup.feature.classification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ClassificationRoute(
    classificationViewModel: ClassificationViewModel = hiltViewModel(),
    onClickBackIcon: () -> Unit,
) {
    val state by classificationViewModel.collectAsState()

    ClassificationScreen(
        state = state,
        onClickChip = classificationViewModel::changeCategory,
        onClickDeleteButton = classificationViewModel::deleteSelectedItem,
        onClickMoveButton = classificationViewModel::moveSelectedItem,
        onClickAllItemMoveButton = classificationViewModel::moveAllItems,
        onClickBackIcon = onClickBackIcon,
    )
}

@Composable
fun ClassificationScreen(
    state: ClassificationState,
    modifier: Modifier = Modifier,
    onClickChip: () -> Unit = {},
    onClickDeleteButton: (Int) -> Unit = {},
    onClickMoveButton: (Int) -> Unit = {},
    onClickAllItemMoveButton: () -> Unit = {},
    onClickBackIcon: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.ai_classification_title),
            isTitleCenter = true,
            onClickBackIcon = { onClickBackIcon() },
        )
        DoraChips(
            modifier = modifier.fillMaxWidth(),
            chipList = listOf(
                DoraChipUiModel(
                    title = "전체 99+",
                    icon = com.mashup.dorabangs.core.designsystem.R.drawable.ic_plus,
                ),
                DoraChipUiModel(
                    title = "전체 99+",
                    icon = com.mashup.dorabangs.core.designsystem.R.drawable.ic_plus,
                ),
            ),
            selectedIndex = 0,
            onClickChip = { onClickChip() },
        )
        ClassificationListScreen(
            state = state,
            onClickDeleteButton = onClickDeleteButton,
            onClickMoveButton = onClickMoveButton,
            onClickAllItemMoveButton = onClickAllItemMoveButton,
        )
    }
}

@Preview
@Composable
fun PreviewClassificationScreen() {
    ClassificationScreen(state = ClassificationState(), onClickBackIcon = {})
}
