package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun MoreButtonBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        firstItemName: Int,
        secondItemName: Int,
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
                DoraFolderListItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 46.dp),
                    items = listOf(
                        BottomSheetItemUIModel(
                            icon = R.drawable.ic_trashbin,
                            itemName = stringResource(id = firstItemName),
                            color = DoraColorTokens.Alert,
                        ),
                        BottomSheetItemUIModel(
                            icon = R.drawable.ic_edit,
                            itemName = stringResource(id = secondItemName),
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun MovingFolderBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        btnEnable: Boolean,
        folderList: List<SelectableBottomSheetItemUIModel>,
        onDismissRequest: () -> Unit,
        onClickCreateFolder: () -> Unit,
        onClickMoveFolder: (String) -> Unit,
        onClickCompleteButton: () -> Unit,
    ) {
        if (isShowSheet) {
            DoraBaseBottomSheet(
                modifier = modifier.fillMaxHeight(0.6f),
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
                                icon = R.drawable.ic_add_folder_purple,
                                itemName = stringResource(id = R.string.moving_folder_dialog_add_folder),
                                isSelected = false,
                                color = DoraColorTokens.Primary,
                            ),
                            onClickItem = onClickCreateFolder,
                            isLastItem = false,
                        )
                    }

                    items(folderList.size) { index ->
                        DoraBottomSheetFolderItem(
                            modifier = Modifier.fillMaxWidth(),
                            data = folderList[index],
                            onClickItem = {
                                onClickMoveFolder(folderList[index].id)
                            },
                            isLastItem = index == folderList.lastIndex,
                        )
                    }

                    item {
                        DoraButtons.DoraBtnMaxFull(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            buttonText = stringResource(id = R.string.moving_folder_bottom_sheet_complete),
                            enabled = btnEnable,
                            onClickButton = onClickCompleteButton,
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
        @StringRes firstItemName: Int,
        @StringRes secondItemName: Int,
        onClickDeleteLink: () -> Unit,
        onClickMoveFolder: () -> Unit,
        onDismissRequest: () -> Unit,
    )

    @Composable
    fun MovingFolderBottomSheet(
        modifier: Modifier,
        isShowSheet: Boolean,
        btnEnable: Boolean,
        folderList: List<SelectableBottomSheetItemUIModel>,
        onDismissRequest: () -> Unit,
        onClickCreateFolder: () -> Unit,
        onClickMoveFolder: (String) -> Unit,
        onClickCompleteButton: () -> Unit,
    )
}

enum class ExpandedType {
    MIN,
    MAX,
}
