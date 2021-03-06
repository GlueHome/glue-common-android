package com.gluehome.common.domain.framework.interactor

import com.gluehome.common.domain.exceptions.Failure
import com.gluehome.common.domain.framework.functional.Either
import com.gluehome.common.threads.RxThreads
import io.reactivex.Flowable

/**
 * FLOWABLE USE CASE
 */
abstract class FlowableUseCase<Type, in Params>(private val threads: RxThreads) where Type : Any {

    abstract fun run(params: Params): Flowable<Either<Failure, Type>>

    open operator fun invoke(params: Params): Flowable<Either<Failure, Type>> {
        return run(params)
            .subscribeOn(threads.executionThread())
            .observeOn(threads.postExecutionThread())
    }
}
