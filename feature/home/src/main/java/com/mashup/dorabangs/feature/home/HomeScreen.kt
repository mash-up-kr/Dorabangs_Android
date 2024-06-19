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
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
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
        
        Text(text = "H3 Bold", style = DoraTypoTokens.H3Bold)
        Text(text = "H3 Medium", style = DoraTypoTokens.H3Medium)
        Text(text = "H3 Normal", style = DoraTypoTokens.H3Normal)
    }
}
