package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoraBaseBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = BottomSheetColorTokens.MoreViewBackgroundColor,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        containerColor = containerColor,
        shape = DoraRoundTokens.Round16,
        dragHandle = { DoraDragHandle(Modifier.padding(top = 6.dp)) },
        onDismissRequest = onDismissRequest,
    ) {
        content()
    }
}

@Composable
private fun DoraDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(36.dp)
            .height(5.dp)
            .background(
                color = BottomSheetColorTokens.HandleColor,
                shape = DoraRoundTokens.Round99,
            ),
    )
}
