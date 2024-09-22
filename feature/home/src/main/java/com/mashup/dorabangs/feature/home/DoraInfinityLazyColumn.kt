package com.mashup.dorabangs.feature.home

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DoraInfinityLazyColumn(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    isLoadAvail: Boolean = true,
    onReachedBottom: () -> Unit = {},
    content: LazyListScope.() -> Unit = {},
) {
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == scrollState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && isLoadAvail) {
            onReachedBottom.invoke()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        content()
    }
}