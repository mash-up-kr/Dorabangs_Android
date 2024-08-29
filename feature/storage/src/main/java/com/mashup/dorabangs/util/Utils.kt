package com.mashup.dorabangs.util

import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.domain.model.FolderType

fun getFolderIcon(type: FolderType): Int {
    return when (type) {
        FolderType.ALL -> R.drawable.ic_3d_all_big
        FolderType.FAVORITE -> R.drawable.ic_3d_bookmark_big
        FolderType.DEFAULT -> R.drawable.ic_3d_pin_big
        FolderType.CUSTOM -> R.drawable.ic_3d_folder_big
        else -> R.drawable.ic_3d_folder_big
    }
}

fun getCacheKey(firstKey: String, secondKey: String) = "$firstKey|$secondKey"
