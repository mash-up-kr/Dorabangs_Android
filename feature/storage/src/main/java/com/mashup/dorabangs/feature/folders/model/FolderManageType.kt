package com.mashup.dorabangs.feature.folders.model

import androidx.annotation.StringRes
import com.mashup.dorabangs.feature.storage.R

enum class FolderManageType(@StringRes val title: Int) {
    CREATE(R.string.storage_create_folder_title),
    CHANGE(R.string.storage_edit_folder_title),
    NOTHING(R.string.storage),
}
