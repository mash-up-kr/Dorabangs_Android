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
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    classificationViewModel: ClassificationViewModel = hiltViewModel(),
) {
    val state by classificationViewModel.collectAsState()

    ClassificationScreen(
        state = state,
        onClickChip = classificationViewModel::changeCategory,
        onClickDeleteButton = classificationViewModel::deleteSelectedItem,
        onClickMoveButton = classificationViewModel::moveSelectedItem,
        onClickAllItemMoveButton = classificationViewModel::moveAllItems,
        onClickBackIcon = onClickBackIcon,
        navigateToHome = navigateToHome,
    )
}

@Composable
fun ClassificationScreen(
    state: ClassificationState,
    onClickChip: () -> Unit,
    onClickDeleteButton: (Int) -> Unit,
    onClickMoveButton: (Int) -> Unit,
    onClickAllItemMoveButton: () -> Unit,
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalCount = if (state.chipState.totalCount > 99) "99+" else state.chipState.totalCount.toString()
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
                    "",
                    "전체 $totalCount",
                    com.mashup.dorabangs.core.designsystem.R.drawable.ic_3d_all_small,
                ),
            ) + state.chipState.chipList,
            selectedIndex = 0,
            onClickChip = { onClickChip() },
        )
        if (state.isClassificationComplete) {
            ClassificationCompleteScreen(navigateToHome = navigateToHome)
        } else {
            ClassificationListScreen(
                state = state,
                onClickDeleteButton = onClickDeleteButton,
                onClickMoveButton = onClickMoveButton,
                onClickAllItemMoveButton = onClickAllItemMoveButton,
            )
        }
    }
}

@Preview
@Composable
fun PreviewClassificationScreen() {
    ClassificationScreen(
        state = ClassificationState(),
        onClickChip = {},
        onClickDeleteButton = {},
        onClickMoveButton = {},
        onClickAllItemMoveButton = {},
        onClickBackIcon = {},
        navigateToHome = {},
    )
}
