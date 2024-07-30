package com.mashup.dorabangs.feature.util

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel.Companion.convertCreatedSecond
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DelayTimer(
    private val scope: CoroutineScope,
    private val action: () -> Unit,
) {
    private var isDelaying = false
    private val timerQueueChannel = Channel<DelayRequest>()
    private val timerQueue = mutableListOf<DelayRequest>()

    init {
        timerQueueChannel
            .receiveAsFlow()
            .onEach { processDelayTime(it) }
            .launchIn(scope)
    }

    suspend fun delaySecond(delayRequest: DelayRequest) {
        timerQueueChannel.send(delayRequest)
    }

    private fun getDelayTime(delays: List<DelayRequest>): Int {
        if (delays.isEmpty()) return -1
        val delaySecond = delays.maxOfOrNull { it.timeStamp.convertCreatedSecond() } ?: 0
        return maxOf(1, delaySecond)
    }

    private suspend fun processDelayTime(delayRequest: DelayRequest) {
        timerQueue.add(delayRequest)

        if (isDelaying) return
        isDelaying = true

        scope.launch {
            val delaySecond = getDelayTime(timerQueue)
            timerQueue.clear()

            delay(delaySecond.toMilliSecond())
            action.invoke()
            isDelaying = false
        }
    }
}

private fun Int.toMilliSecond() = minOf(8, this) * 1000L

data class DelayRequest(
    val timeStamp: String,
)
