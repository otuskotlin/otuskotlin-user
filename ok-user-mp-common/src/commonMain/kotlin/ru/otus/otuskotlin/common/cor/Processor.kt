package ru.otus.otuskotlin.common.cor

class Processor<T> private constructor(
        private val matcher: CorMatcher<T>,
        private val onError: CorOnError<T>,
        private val handlers: Collection<IExec<T>>
) : IExec<T> {
    override suspend fun exec(ctx: T) {
        try {
            if (matcher(ctx)) {
                handlers.forEach {
                    it.exec(ctx)
                }
            }
        } catch (e: Throwable) {
            onError(ctx, e)
        }
    }

    @CorDslMarker
    class Builder<T> {
        private var matcher: CorMatcher<T> = { true }
        private var onError: CorOnError<T> = {e -> throw e }
        private val handlers: MutableList<IExec<T>> = mutableListOf()

        fun isApplicable(block: CorMatcher<T>) {
            matcher = block
        }

        fun onError(block: CorOnError<T>) {
            onError = block
        }

        fun handler(block: Handler.Builder<T>.() -> Unit) = handlers.add(corHandler(block))
        fun exec(block: IExec<T>) = handlers.add(block)
        fun exec(block: CorHandler<T>) {
            handlers.add(
                    corHandler {
                        exec(block)
                    }
            )
        }
        fun processor(block: Processor.Builder<T>.() -> Unit) = handlers.add(cor(block))

        fun build() = Processor(
                matcher = matcher,
                onError = onError,
                handlers = handlers
        )
    }

}
