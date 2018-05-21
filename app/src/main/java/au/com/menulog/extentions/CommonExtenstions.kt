package au.com.menulog.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)
}

fun <T> Single<T>.uiSubscription(onNext: (it: T)-> Unit, onError: (it: Throwable)-> Unit) : Disposable{
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe (onNext,onError)
}














