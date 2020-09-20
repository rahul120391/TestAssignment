package com.cardinalHealth.test.network.baseusecase


class UseCaseHandler(
        private val mUseCaseScheduler: UseCaseScheduler
) {

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>
    ){
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)
         mUseCaseScheduler.execute(Runnable {
            useCase.run()
        })
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> subscribe(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackSubscribeWrapper(callback, this)

        useCase.run()
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> stopSubscription(
            useCase: UseCase<T, R>
    ) {
        useCase.stopSubscription()
    }


    private fun <V : UseCase.ResponseValue> notifySubscribeResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onSuccess(response)
    }

    private fun <V : UseCase.ResponseValue> notifySubscribeError(
            useCaseCallback: UseCase.UseCaseCallback<V>,code:Int
    ) {
        useCaseCallback.onError(code)
    }

    private fun <V : UseCase.ResponseValue> notifyResponse(
            response: V,
            useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValue> notifyError(
            useCaseCallback: UseCase.UseCaseCallback<V>,
            code:Int
    ) {
        mUseCaseScheduler.onError(useCaseCallback,code)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(
            private val mCallback: UseCase.UseCaseCallback<V>,
            private val mUseCaseHandler: UseCaseHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError(code: Int) {
            mUseCaseHandler.notifyError(mCallback,code)
        }
    }

    private class UiCallbackSubscribeWrapper<V : UseCase.ResponseValue>(
            private val mCallback: UseCase.UseCaseCallback<V>,
            private val mUseCaseHandler: UseCaseHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifySubscribeResponse(response, mCallback)
        }

        override fun onError(code: Int) {
            mUseCaseHandler.notifySubscribeError(mCallback,code)
        }
    }

    companion object {
        private var INSTANCE: UseCaseHandler? = null
        fun getInstance(): UseCaseHandler {
            if (INSTANCE == null) {
                INSTANCE = UseCaseHandler(UseCaseThreadPoolScheduler())
            }
            return INSTANCE?:UseCaseHandler(UseCaseThreadPoolScheduler())
        }
    }
}