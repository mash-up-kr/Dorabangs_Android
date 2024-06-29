package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

object DoraBottomSheet : BottomSheetType {

    @Composable
    override fun MoreButtonBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        onClickDeleteLinkButton: () -> Unit,
        onClickMoveFolderButton: () -> Unit,
        onDismissRequest: () -> Unit,
    ) {
        if (isShowSheet) {
            DoraBaseBottomSheet(
                modifier = modifier,
                containerColor = BottomSheetColorTokens.MoreViewBackgroundColor,
                onDismissRequest = onDismissRequest,
            ) {
                DoraBottomSheetItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 46.dp),
                    items = listOf(
                        BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 삭제", color = DoraColorTokens.Alert),
                        BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 이름 변경"),
                    ),
                    onClickItem = { index ->
                        if (index == 0) {
                            onClickDeleteLinkButton()
                        }
                        if (index == 1) {
                            onClickMoveFolderButton()
                        }
                    },
                )
            }
        }
    }
}

sealed interface BottomSheetType {

    @Composable
    fun MoreButtonBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        onClickDeleteLink: () -> Unit,
        onClickMoveFolder: () -> Unit,
        onDismissRequest: () -> Unit,
    )
}
