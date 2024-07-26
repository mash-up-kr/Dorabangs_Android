package com.mashup.dorabangs.core.designsystem.component.toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.IcSelect
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.ImgSelect
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens.caption3Bold
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme

@Composable
fun DoraToast(
    text: String,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackBarHostState,
    ) {
        ToastContent(text)
    }
}

/**
 * https://www.figma.com/design/YsaM1pg9e5elUOLUEbnDmR/Linkit_Design-ground?node-id=3779-67550&t=wpKhuQrnpn6vfyUg-0
 */
@Composable
fun ToastContent(
    text: String,
    modifier: Modifier = Modifier,
    style: ToastStyle = ToastStyle.CHECK, // style보고 앞에 v인지 ! 인지 구분 예정
) {
    Row(
        modifier = modifier
            .clip(shape = DoraRoundTokens.Round99)
            .background(DoraColorTokens.G9)
            .padding(all = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = if (style == ToastStyle.CHECK) ImgSelect.IcSelect else ImgSelect.IcSelect,
            contentDescription = "",
        )
        Spacer(
            modifier = Modifier.width(width = 8.dp),
        )
        Text(
            text = text,
            style = caption3Bold,
            textAlign = TextAlign.Center,
            color = DoraColorTokens.G2,
        )
    }
}

enum class ToastStyle {
    CHECK, ALERT
}

@Composable
@Preview
fun DoraToastPreview() {
    DorabangsTheme {
        ToastContent(
            text = "글자 수에 따라 Toast 가변 최대 마진 2 durl",
        )
    }
}