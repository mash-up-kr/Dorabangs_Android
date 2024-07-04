package com.dorabangs.feature.save.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorabangs.feature.save.R
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderItem
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderListItems
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.Folder
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.ImgFolder
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens

var sampleList = listOf(
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "새 폴더",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = true,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
    DoraSelectableFolderItem(
        Folder.ImgFolder,
        itemName = "폴더 이름",
        isSelected = false,
    ),
)

@Composable
fun DoraLinkSaveSelectFolderScreen(
    url: String,
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // 테스트용 임시 값입니다
    var list by remember {
        mutableStateOf(
            sampleList,
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = LinkSaveColorTokens.ContainerColor),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier,
                title = stringResource(id = R.string.link_save_title_text),
                isTitleCenter = true,
                onClickBackIcon = onClickBackIcon,
            )
            DoraLinkSaveTitleAndLinkScreen(url = url)
            DoraSelectableFolderListItems(
                modifier = Modifier,
                items = list,
                onClickItem = { searchIndex ->
                    // TODO 임시 로직입니다
                    list = sampleList.mapIndexed { index, doraSelectableFolderItem ->
                        if (index == searchIndex) {
                            doraSelectableFolderItem.copy(isSelected = true)
                        } else doraSelectableFolderItem.copy(isSelected = false)
                    }
                },
            )
        }
        Column {
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                buttonText = stringResource(id = R.string.link_save_button_text),
                enabled = sampleList.any { it.isSelected },
                onClickButton = onClickSaveButton,
            )
        }
    }
}

@Composable
@Preview
fun DoraLinkSaveSelectFolderScreenPreview() {
    DoraLinkSaveSelectFolderScreen(
        url = "https://www.naver.com/articale 길면 넌 바보다",
        onClickBackIcon = {},
        onClickSaveButton = {},
    )
}
