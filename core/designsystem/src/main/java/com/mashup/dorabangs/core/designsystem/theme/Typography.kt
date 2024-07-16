package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mashup.dorabangs.core.designsystem.R

object DoraFontSizeTokens {
    val H3 = 48.sp
    val H4 = 34.sp
    val H5 = 32.sp
    val H6 = 28.sp
    val Title = 24.sp
    val SubTitle1 = 20.sp
    val SubTitle2 = 18.sp
    val Base1 = 16.sp
    val Base2 = 15.sp
    val Caption3 = 14.sp
    val Caption2 = 13.sp
    val Caption1 = 12.sp
    val S = 11.sp
    val XS = 10.sp
}

object DoraLineHeightTokens {
    val H3 = 54.sp
    val H4 = 46.sp
    val H5 = 38.sp
    val H6 = 38.sp
    val Title = 34.sp
    val SubTitle1 = 26.sp
    val SubTitle2 = 28.sp
    val Base1 = 24.sp
    val Base2 = 24.sp
    val Caption3 = 22.sp
    val Caption2 = 22.sp
    val Caption1 = 22.sp
    val S = 14.sp
    val XS = 14.sp
}

object DoraTypoTokens {
    private val doraFontFamily = FontFamily(
        Font(R.font.nanum_700, FontWeight.W700),
        Font(R.font.nanum_500, FontWeight.W500),
        Font(R.font.nanum_400, FontWeight.W400),
    )

    val H3Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.H3,
        lineHeight = DoraLineHeightTokens.H3,
    )

    val H3Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.H3,
        lineHeight = DoraLineHeightTokens.H3,
    )

    val H3Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.H3,
        lineHeight = DoraLineHeightTokens.H3,
    )

    val H4Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.H4,
        lineHeight = DoraLineHeightTokens.H4,
    )

    val H4Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.H4,
        lineHeight = DoraLineHeightTokens.H4,
    )

    val H4Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.H4,
        lineHeight = DoraLineHeightTokens.H4,
    )

    val H5Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.H5,
        lineHeight = DoraLineHeightTokens.H5,
    )

    val H5Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.H5,
        lineHeight = DoraLineHeightTokens.H5,
    )

    val H5Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.H5,
        lineHeight = DoraLineHeightTokens.H5,
    )

    val H6Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.H6,
        lineHeight = DoraLineHeightTokens.H6,
    )

    val H6Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.H6,
        lineHeight = DoraLineHeightTokens.H6,
    )

    val H6Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.H6,
        lineHeight = DoraLineHeightTokens.H6,
    )

    val TitleBold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Title,
        lineHeight = DoraLineHeightTokens.Title,
    )

    val TitleMedium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Title,
        lineHeight = DoraLineHeightTokens.Title,
    )

    val TitleNormal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Title,
        lineHeight = DoraLineHeightTokens.Title,
    )

    val Subtitle1Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.SubTitle1,
        lineHeight = DoraLineHeightTokens.SubTitle1,
    )

    val Subtitle1Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.SubTitle1,
        lineHeight = DoraLineHeightTokens.SubTitle1,
    )

    val Subtitle1Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.SubTitle1,
        lineHeight = DoraLineHeightTokens.SubTitle1,
    )

    val Subtitle2Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.SubTitle2,
        lineHeight = DoraLineHeightTokens.SubTitle2,
    )

    val Subtitle2Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.SubTitle2,
        lineHeight = DoraLineHeightTokens.SubTitle2,
    )

    val Subtitle2Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.SubTitle2,
        lineHeight = DoraLineHeightTokens.SubTitle2,
    )

    val base1Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Base1,
        lineHeight = DoraLineHeightTokens.Base1,
    )

    val base1Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Base1,
        lineHeight = DoraLineHeightTokens.Base1,
    )

    val base1Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Base1,
        lineHeight = DoraLineHeightTokens.Base1,
    )

    val base2Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Base2,
        lineHeight = DoraLineHeightTokens.Base2,
    )

    val base2Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Base2,
        lineHeight = DoraLineHeightTokens.Base2,
    )

    val base2Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Base2,
        lineHeight = DoraLineHeightTokens.Base2,
    )

    val caption3Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Caption3,
        lineHeight = DoraLineHeightTokens.Caption3,
    )

    val caption3Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Caption3,
        lineHeight = DoraLineHeightTokens.Caption3,
    )

    val caption3Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Caption3,
        lineHeight = DoraLineHeightTokens.Caption3,
    )

    val caption2Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Caption2,
        lineHeight = DoraLineHeightTokens.Caption2,
    )

    val caption2Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Caption2,
        lineHeight = DoraLineHeightTokens.Caption2,
    )

    val caption2Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Caption2,
        lineHeight = DoraLineHeightTokens.Caption2,
    )

    val caption1Bold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.Caption1,
        lineHeight = DoraLineHeightTokens.Caption1,
    )

    val caption1Medium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.Caption1,
        lineHeight = DoraLineHeightTokens.Caption1,
    )

    val caption1Normal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.Caption1,
        lineHeight = DoraLineHeightTokens.Caption1,
    )

    val SBold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.S,
        lineHeight = DoraLineHeightTokens.S,
    )

    val SMedium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.S,
        lineHeight = DoraLineHeightTokens.S,
    )

    val SNormal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.S,
        lineHeight = DoraLineHeightTokens.S,
    )

    val XSBold = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = DoraFontSizeTokens.XS,
        lineHeight = DoraLineHeightTokens.XS,
    )

    val XSMedium = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = DoraFontSizeTokens.XS,
        lineHeight = DoraLineHeightTokens.XS,
    )

    val XSNormal = TextStyle(
        fontFamily = doraFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = DoraFontSizeTokens.XS,
        lineHeight = DoraLineHeightTokens.XS,
    )
}
