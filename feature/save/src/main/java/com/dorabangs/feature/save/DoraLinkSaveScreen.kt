package com.dorabangs.feature.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.textfield.DoraTextField

@Composable
fun DoraLinkSaveScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        DoraTextField(
            text = "",
            hintText = stringResource(id = R.string.link_save_hint_text),
            labelText = stringResource(id = R.string.link_save_label_text),
            helperText = stringResource(id = R.string.link_save_error_text),
            helperEnabled = true, // 서버통신 이후에 알 수 있음
        )
        Spacer(modifier = Modifier.height(20.dp))
        DoraButtons.DoraBtnMaxFull(
            modifier = Modifier
                .fillMaxWidth(),
            buttonText = "저장",
            enabled = true,
            onClickButton = {},
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
@Preview
fun DoraLinkSaveScreenPreview() {
    DoraLinkSaveScreen()
}