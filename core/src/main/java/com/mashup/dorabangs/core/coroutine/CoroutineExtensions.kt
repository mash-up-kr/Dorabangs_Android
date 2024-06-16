package com.mashup.dorabangs.core.coroutine

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    // timber 넣을랬는뎅 물어보고 넣자~
    Log.e("Dorabangs", "dorabangs exception: ${throwable.message}", throwable)
    when (throwable) {
        is RuntimeException -> {
            // 예시 공통 다이얼로그?
        }

        else -> {
            // TODO
        }
    }
}

fun CoroutineScope.doraLaunch(
    block: suspend CoroutineScope.() -> Unit,
) = launch(context = coroutineContext + coroutineExceptionHandler) {
    block.invoke(this)
}
