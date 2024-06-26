package com.mashup.dorabangs.feature.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.R
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun StorageRoute() {

}

@Composable
fun StorageScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(color = DoraColorTokens.G1)
    ) {
        StorageTopAppBar()
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            StorageDefaultFolder()
            Spacer(modifier = Modifier.height(20.dp))
            StorageCustomFolder()
        }
    }
}

@Composable
fun StorageTopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "보관함",
            style = DoraTypoTokens.base1Bold
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_call_answer),
            contentDescription = "folderIcon"
        )
    }
}

@Composable
fun StorageDefaultFolder(
    list: List<String> = listOf("다연", "석주", "호현")
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.P1, shape = DoraRoundTokens.Round12)
            .padding(horizontal = 12.dp)
    ) {
        list.forEachIndexed { idx, item ->
            StorageListItem(title = item, count = 1, isDefaultFolder = true)
            if(idx != list.lastIndex) {
                HorizontalDivider(modifier = Modifier
                    .height(1.dp)
                    .background(color = DoraColorTokens.G1))
            }
        }

    }
}

@Composable
fun StorageCustomFolder(
    list: List<String> = listOf("다연", "석주", "호현")
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .background(color = DoraColorTokens.P1, shape = DoraRoundTokens.Round12)
            .padding(horizontal = 12.dp)
    ) {
        itemsIndexed(list) { idx, item ->
            StorageListItem(title = item, count = 1, isDefaultFolder = false)
            if(idx != list.lastIndex) {
                HorizontalDivider(modifier = Modifier
                    .height(1.dp)
                    .background(color = DoraColorTokens.G1))
            }
        }
    }
}

@Preview
@Composable
fun PreviewStorageScreen() {
    StorageScreen()
}