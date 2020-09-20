package com.cardinalHealth.test.network.baseusecase


abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    var requestValues: Q? = null

    var useCaseCallback: UseCaseCallback<P>? = null

    internal fun run(){
        return executeUseCase(requestValues)
    }

    internal fun stopSubscription() {
        stopExecution()
    }

    protected abstract fun executeUseCase(requestValues: Q?)
    protected open fun stopExecution() {}


    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)
        fun onError(code:Int)
    }
}