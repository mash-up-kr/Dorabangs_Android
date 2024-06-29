package com.mashup.dorabangs.feature.storage.storage.model

data class StorageListState(
    val defaultStorageFolderList: List<StorageFolderItem> = listOf(),
    val customStorageFolderList: List<StorageFolderItem> = listOf(),
) {
    companion object {
        fun defaultFolderList() =
            listOf(
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),
            )
        fun customFolderList() =
            listOf(
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.ALL, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.FAVORITE, postCount = 0),
                StorageFolderItem(folderTitle = StorageDefaultFolder.LATER_READ, postCount = 0),

            )
    }
}

data class StorageFolderItem(
    val folderTitle: StorageDefaultFolder,
    val postCount: Int = 0,
)

enum class StorageDefaultFolder(val title: String) {
    ALL("모든 링크"),
    FAVORITE("즐겨찾기"),
    LATER_READ("나중에 읽을 링크"),
}
