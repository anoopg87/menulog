package au.com.menulog.base

import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface RXCallManager {

    fun <T> uiSubscribe(single: Single<T>, onNext: (it: T)-> Unit, onError: (it: Throwable)-> Unit) : Disposable

}