package dev.kord.gateway.handler

import dev.kord.gateway.Close
import dev.kord.gateway.DispatchEvent
import dev.kord.gateway.Event
import dev.kord.gateway.Sequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

internal class SequenceHandler(
    flow: Flow<Event>,
    private val sequence: Sequence,
    parentContext: CoroutineContext = SupervisorJob() + Dispatchers.Default,
) : Handler(flow, "SequenceHandler", parentContext) {

    init {
        on<DispatchEvent> { event ->
            sequence.value = event.sequence ?: sequence.value
        }

        on<Close.SessionReset> {
            sequence.value = null
        }
    }

}