package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

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
                        BottomSheetItemUIModel(
                            icon = R.drawable.ic_plus,
                            itemName = stringResource(id = R.string.more_button_bottom_sheet_remove_link),
                            color = DoraColorTokens.Alert,
                        ),
                        BottomSheetItemUIModel(
                            icon = R.drawable.ic_plus,
                            itemName = stringResource(id = R.string.more_button_bottom_sheet_moving_folder),
                        ),
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

    @Composable
    override fun MovingFolderBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        folderList: List<SelectableBottomSheetItemUIModel>,
        onDismissRequest: () -> Unit,
    ) {
        if (isShowSheet) {
            DoraBaseBottomSheet(
                modifier = modifier,
                containerColor = BottomSheetColorTokens.MovingFolderColor,
                onDismissRequest = onDismissRequest,
            ) {
                LazyColumn {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.moving_folder_dialog_title),
                            style = DoraTypoTokens.base2Bold,
                        )
                    }

                    item {
                        DoraBottomSheetFolderItem(
                            modifier = Modifier.fillMaxWidth(),
                            data = SelectableBottomSheetItemUIModel(
                                icon = R.drawable.ic_plus,
                                itemName = stringResource(id = R.string.moving_folder_dialog_add_folder),
                                isSelected = false,
                            ),
                        )
                    }

                    items(folderList.size) { index ->
                        DoraBottomSheetFolderItem(
                            modifier = Modifier.fillMaxWidth(),
                            data = folderList[index],
                        )
                    }

                    item {
                        DoraButtons.DoraBtnMaxFull(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            buttonText = stringResource(id = R.string.moving_folder_bottom_sheet_complete),
                            enabled = true,
                            onClickButton = onDismissRequest,
                        )
                    }
                }
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

    @Composable
    fun MovingFolderBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        folderList: List<SelectableBottomSheetItemUIModel>,
        onDismissRequest: () -> Unit,
    )
}
