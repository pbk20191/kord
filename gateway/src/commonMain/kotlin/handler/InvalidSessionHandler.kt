package dev.kord.gateway.handler

import dev.kord.gateway.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

internal class InvalidSessionHandler(
    flow: Flow<Event>,
    parentContext: CoroutineContext = SupervisorJob() + Dispatchers.Default,
    private val restart: suspend (event: Close) -> Unit
) : Handler(flow, "InvalidSessionHandler", parentContext) {

    override fun start() {
        on<InvalidSession> {
            if (it.resumable) restart(Close.Reconnecting)
            else restart(Close.SessionReset)
        }
    }

}