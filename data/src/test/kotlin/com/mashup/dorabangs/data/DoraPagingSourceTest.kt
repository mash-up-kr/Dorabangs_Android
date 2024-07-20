package com.mashup.dorabangs.data

import com.mashup.dorabangs.data.pagingsource.DoraPagingSource
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class DoraPagingSourceTest {

    val pagingSource: DoraPagingSource<SavedLinkDetailInfo> = mockk()

    @Test
    fun `페이징로드테스트`() = runTest {

        //Excutor를 mocking한다. -> load안에 넣는다~~

        //pagingSource.load()
    }
}