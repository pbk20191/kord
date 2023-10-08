package dev.kord.gateway.handler

import dev.kord.gateway.Event
import dev.kord.gateway.Reconnect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

internal class ReconnectHandler(
    flow: Flow<Event>,
    parentContext: CoroutineContext = SupervisorJob() + Dispatchers.Default,
    private val reconnect: suspend () -> Unit
) : Handler(flow, "ReconnectHandler", parentContext) {

    override fun start() {
        on<Reconnect> {
            reconnect()
        }
    }
}
