package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoraBaseBottomSheet(
    state: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    modifier: Modifier = Modifier,
    dragHandler: @Composable (() -> Unit),
    containerColor: Color = BottomSheetColorTokens.MoreViewBackgroundColor,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        sheetState = state,
        modifier = modifier,
        containerColor = containerColor,
        shape = DoraRoundTokens.TopRound16,
        dragHandle = dragHandler,
        onDismissRequest = onDismissRequest,
    ) {
        content()
    }
}

@Composable
fun DoraLightDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(36.dp)
            .height(5.dp)
            .background(
                color = BottomSheetColorTokens.LightHandleColor,
                shape = DoraRoundTokens.Round99,
            ),
    )
}

@Composable
fun DoraDarkDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(36.dp)
            .height(5.dp)
            .background(
                color = BottomSheetColorTokens.DarkHandleColor,
                shape = DoraRoundTokens.Round99,
            ),
    )
}
