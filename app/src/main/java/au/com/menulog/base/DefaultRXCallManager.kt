package au.com.menulog.base

import au.com.menulog.extentions.uiSubscription
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class DefaultRXCallManager : RXCallManager {
    override fun <T> uiSubscribe(single: Single<T>, onNext: (it: T) -> Unit, onError: (it: Throwable) -> Unit): Disposable = single.uiSubscription(onNext, onError)
}