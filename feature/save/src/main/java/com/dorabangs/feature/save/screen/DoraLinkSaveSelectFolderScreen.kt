package com.dorabangs.feature.save.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorabangs.feature.save.DoraSaveState
import com.dorabangs.feature.save.R
import com.dorabangs.feature.save.SelectableFolder
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderItem
import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderListItems
import com.mashup.dorabangs.core.designsystem.component.folder.icnewfolder.IcNewFolder
import com.mashup.dorabangs.core.designsystem.component.folder.icnewfolder.NewFolder
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens

@Composable
fun DoraLinkSaveSelectFolderScreen(
    state: DoraSaveState,
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    onClickItem: (Int) -> Unit,
    getInitialFolder: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = Unit) {
        getInitialFolder()
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
            DoraLinkSaveTitleAndLinkScreen(state = state)
            DoraSelectableFolderListItems(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState()),
                items = state.folderList.toSelectableItems(),
                onClickItem = onClickItem,
            )
        }
        Column {
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                buttonText = stringResource(id = R.string.link_save_button_text),
                enabled = state.folderList.any { it.isSelected },
                onClickButton = onClickSaveButton,
            )
        }
    }
}

fun List<SelectableFolder>.toSelectableItems() = map {
    DoraSelectableFolderItem(
        itemName = it.name,
        isSelected = it.isSelected,
        vector = NewFolder.IcNewFolder,
    )
}

@Composable
@Preview
fun DoraLinkSaveSelectFolderScreenPreview() {
    DoraLinkSaveSelectFolderScreen(
        state = DoraSaveState(
            urlLink = "https://www.naver.com/articale 길면 넌 바보다",
        ),
        onClickBackIcon = {},
        onClickSaveButton = {},
        onClickItem = {},
        getInitialFolder = {},
    )
}
