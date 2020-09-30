package ru.otus.otuskotlin.common.cor

open class Handler<T> private constructor(
        private val matcher: CorMatcher<T> = { true },
        private val handler: CorHandler<T> = {},
        private val onError: CorOnError<T> = {e -> throw e }
) : IExec<T> {

    override suspend fun exec(ctx: T) {
        try {
            if (matcher(ctx)) handler(ctx)
        } catch (e: Throwable) {
            onError(ctx, e)
        }
    }

    @CorDslMarker
    class Builder<T> {
        private var matcher: CorMatcher<T> = { true }
        private var handler: CorHandler<T> = { }
        private var onError: CorOnError<T> = { e -> throw e }

        fun isApplicable(block: CorMatcher<T>) {
            matcher = block
        }

        fun exec(block: CorHandler<T>) {
            handler = block
        }

        fun onError(block: CorOnError<T>) {
            onError = block
        }

        fun build() = Handler<T>(
                matcher = matcher,
                handler = handler,
                onError = onError
        )
    }
}

fun <T> corHandler(block: Handler.Builder<T>.() -> Unit): Handler<T> = Handler.Builder<T>().apply(block).build()
