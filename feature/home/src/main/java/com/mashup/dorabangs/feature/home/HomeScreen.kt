package com.mashup.dorabangs.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        onClickAddButton = { viewModel.add(1) },
        onClickTestButton = { viewModel.test() },
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClickAddButton: () -> Unit = {},
    onClickTestButton: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("홈텍스: ${state.number}")
        Button(onClick = onClickAddButton) {
            Text("Add Button")
        }
        Button(onClick = onClickTestButton) {
            Text("도라방스")
        }
    }
}
