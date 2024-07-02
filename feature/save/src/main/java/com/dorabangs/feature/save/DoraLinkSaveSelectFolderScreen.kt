package com.dorabangs.feature.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.ImgFolder
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.Folder
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderListItems
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderItem
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens

val sampleList = listOf(
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
    onClickBackIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = LinkSaveColorTokens.ContainerColor),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier,
                title = stringResource(id = R.string.link_save_title_text),
                isTitleCenter = true,
                onClickBackIcon = onClickBackIcon,
            )
            DoraLinkSaveTitleAndLinkScreen()
            DoraSelectableFolderListItems(
                modifier = Modifier,
                items = sampleList,
            )
        }
        Column {
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                buttonText = "저장",
                enabled = sampleList.any { it.isSelected },
                onClickButton = {
                    TODO("저장했더잉")
                }
            )
        }
    }
}

@Composable
@Preview
fun DoraLinkSaveSelectFolderScreenPreview() {
    DoraLinkSaveSelectFolderScreen(
        {},
    )
}