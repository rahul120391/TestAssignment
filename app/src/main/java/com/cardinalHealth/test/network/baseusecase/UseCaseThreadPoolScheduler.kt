package com.cardinalHealth.test.network.baseusecase

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadPoolScheduler : UseCaseScheduler {

    companion object{
        private const val POOL_SIZE = 6
        private const val MAX_POOL_SIZE = 8
        private const val TIME_OUT = 30
    }
    private val handler = Handler(Looper.getMainLooper())
    private var threadPoolExecutor: ThreadPoolExecutor
    init {
        threadPoolExecutor = ThreadPoolExecutor(
                POOL_SIZE, MAX_POOL_SIZE, TIME_OUT.toLong(),
                TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE)
        )
    }

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        handler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValue> onError(
            useCaseCallback: UseCase.UseCaseCallback<V>,
            code:Int
    ) {
        handler.post { useCaseCallback.onError(code) }
    }

}