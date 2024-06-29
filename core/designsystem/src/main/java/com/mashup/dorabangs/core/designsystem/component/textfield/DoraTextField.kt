package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.CloseCircle
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.DoraIconClose
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TextFieldColorTokens

@Composable
fun DoraTextField(
    text: String,
    hintText: String,
    labelText: String,
    modifier: Modifier = Modifier,
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length),
            ),
        )
    }

    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        DoraLabel(labelText = labelText)
        Spacer(modifier = Modifier.height(height = 8.dp))
        Column(
            modifier = Modifier
                .size(width = 350.dp, height = 48.dp)
                .clip(DoraRoundTokens.Round8)
                .background(TextFieldColorTokens.TextFieldBackGroundColor),
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp, vertical = 13.dp),
                value = textFieldValue,
                singleLine = true,
                textStyle = DoraTypoTokens.caption1Medium,
                onValueChange = {
                    textFieldValue = it
                },
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.width(326.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (textFieldValue.text.isBlank()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = hintText,
                                maxLines = 1,
                                color = TextFieldColorTokens.TextFieldHintTextColor,
                                style = DoraTypoTokens.caption1Medium,
                                textAlign = TextAlign.Start,
                            )
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween, // 양끝 배치
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f), // innerTextField가 가로로 확장되지 않도록 설정
                                ) {
                                    innerTextField()
                                }
                                Spacer(modifier = Modifier.width(width = 4.dp))
                                IconButton(
                                    modifier = Modifier.size(size = 24.dp),
                                    onClick = { textFieldValue = TextFieldValue("") },
                                ) {
                                    Icon(
                                        imageVector = DoraIconClose.CloseCircle,
                                        contentDescription = stringResource(id = R.string.url_text_clear),
                                        tint = TextFieldColorTokens.TextFieldClearButtonColor,
                                    )
                                }
                            }
                        }
                    }
                },
            )
        }
    }


}

@Composable
@Preview
fun DoraTextFieldPreview() {
    DoraTextField(
        text = "테스트용 이다 어쩔래  ? ? ? asogihasio gasiofhgaioshgioashgaosighoasihg",
        hintText = "URL을 입력해주세요.",
        labelText = "바보",
    )
}

@Composable
@Preview
fun DoraTextFieldPreviewWithHint() {
    DoraTextField(
        text = "",
        hintText = "URL을 입력해주세요.",
        labelText = "링크",
    )
}
