package com.jforex.kforexutils.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HotPublisher<T>
{
    private val publisher = PublishSubject.create<T>()
    private val connectableObservable = publisher.publish()
    private val disposable = connectableObservable.connect()

    fun observable(): Observable<T>
    {
        return connectableObservable
    }

    fun onNext(observableInstance: T)
    {
        publisher.onNext(observableInstance)
    }

    fun unsubscribe()
    {
        disposable.dispose()
    }
}