package com.mashup.dorabangs.feature.storage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun StorageListItem(
    title: String,
    count: Int,
    isDefaultFolder: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                contentDescription = "folderIcon"
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = title,
                color = DoraColorTokens.G9,
                style = DoraTypoTokens.caption3Medium
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically),
                text = "$count",
                color = DoraColorTokens.G4,
                style = DoraTypoTokens.caption3Medium
            )
            val icon =
                if(isDefaultFolder) androidx.core.R.drawable.ic_call_answer
                else androidx.core.R.drawable.ic_call_answer
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "folderIcon"
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageListItem() {
    StorageListItem(title = "디자인", count = 3, isDefaultFolder = true)
}