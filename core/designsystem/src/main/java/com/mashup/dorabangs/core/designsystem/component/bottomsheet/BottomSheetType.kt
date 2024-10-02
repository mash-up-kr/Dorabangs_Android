package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
                dragHandler = { DoraDarkDragHandle(Modifier.padding(top = 12.dp)) },
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
        isBtnEnabled: Boolean,
        folderList: List<SelectableBottomSheetItemUIModel>,
        onDismissRequest: () -> Unit,
        onClickCreateFolder: () -> Unit,
        onClickMoveFolder: (SelectableBottomSheetItemUIModel?) -> Unit,
        onClickCompleteButton: () -> Unit,
    ) {
        if (isShowSheet) {
            DoraBaseBottomSheet(
                modifier = modifier.heightIn(max = 441.dp),
                containerColor = BottomSheetColorTokens.MovingFolderColor,
                dragHandler = { DoraLightDragHandle(Modifier.padding(top = 12.dp)) },
                onDismissRequest = onDismissRequest,
            ) {
                Column {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 6.dp, top = 13.dp),
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.moving_folder_dialog_title),
                            color = DoraColorTokens.G8,
                            style = DoraTypoTokens.base2Bold,
                        )
                        LazyColumn {
                            item {
                                DoraBottomSheetFolderItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    data = SelectableBottomSheetItemUIModel(
                                        icon = null,
                                        itemName = stringResource(id = R.string.moving_folder_dialog_add_folder),
                                        isSelected = false,
                                        textStyle = DoraTypoTokens.caption3Normal,
                                        selectedTextStyle = DoraTypoTokens.caption3Bold,
                                        color = DoraColorTokens.Primary500,
                                    ),
                                    onClickItem = onClickCreateFolder,
                                    isLastItem = false,
                                )
                            }

                            items(
                                count = folderList.size,
                                contentType = { "FolderItem" },
                                key = { idx -> folderList[idx].id },
                            ) { index ->
                                DoraBottomSheetFolderItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    data = folderList[index],
                                    onClickItem = {
                                        onClickMoveFolder(folderList.getOrNull(index))
                                    },
                                    isLastItem = index == folderList.lastIndex,
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(DoraColorTokens.White)
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                    ) {
                        DoraButtons.DoraBtnMaxFull(
                            modifier = Modifier
                                .fillMaxWidth(),
                            buttonText = stringResource(id = R.string.moving_folder_bottom_sheet_complete),
                            enabled = isBtnEnabled,
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
        onClickMoveFolder: (SelectableBottomSheetItemUIModel?) -> Unit,
        onClickCompleteButton: () -> Unit,
    )
}

enum class ExpandedType {
    MIN,
    MAX,
}
