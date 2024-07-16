package com.dorabangs.feature.save

import com.mashup.dorabangs.core.designsystem.component.folder.DoraSelectableFolderItem
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.Folder
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.ImgFolder

data class DoraSaveState(
    val title: String = "",
    val urlLink: String = "",
    val thumbnailUrl: String = "",
    val isShortLink: Boolean = false,
    val isError: Boolean = false,
    val folderList: List<DoraSelectableFolderItem> = sampleList,
)

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