package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.DoraUrlCheckRemoteDataSource
import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

class DoraUrlCheckRemoteDataSourceImpl @Inject constructor() : DoraUrlCheckRemoteDataSource {
    override suspend fun checkDataSource(urlLink: String): DoraUrlCheckResponse {
        return checkUrl(urlLink)
    }

    /**
     * 전달 받은 url을 체크합니다.
     * 리다이렉션이 있는 경우, 특히나 short Link 의 경우 헤더의 Location 값이 원 주소이기 때문에 검사합니다
     * 추가로 short Link 에서는 원 링크가 아닌 이상 title, thumbnail 을 가져올 수 없어서,
     * connection을 원 주소로 다시 연결하여 정보를 가져옴
     */
    private suspend fun checkUrl(urlLink: String): DoraUrlCheckResponse = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            var connection = Jsoup.connect(urlLink)
                .followRedirects(false)
                .ignoreContentType(true)

            var response = connection.execute()

            // 리다이렉션이 발생한 경우 Location 헤더 가져오기
            val longUrlLink = response.header("Location")
            if (longUrlLink != null) {
                connection = Jsoup.connect(longUrlLink).followRedirects(true)
                response = connection.execute()
            }

            val doc = response.parse()

            // 오픈 그래프 태그에서 타이틀 추출
            val titleElement: Element? = doc.select("meta[property=og:title]").first()
            val title = titleElement?.attr("content")

            // 오픈 그래프 태그에서 썸네일 URL 추출
            val thumbnailElement: Element? = doc.select("meta[property=og:image]").first()
            val thumbnailUrl = thumbnailElement?.attr("content")

            DoraUrlCheckResponse(
                urlLink = longUrlLink ?: urlLink,
                title = title.orEmpty(),
                thumbnailUrl = thumbnailUrl.orEmpty(),
                isShortLink = longUrlLink == null,
            )
        }.getOrDefault(
            DoraUrlCheckResponse(
                urlLink = urlLink,
                title = "",
                thumbnailUrl = "",
                isShortLink = false,
                isError = true,
            ),
        )
    }
}
