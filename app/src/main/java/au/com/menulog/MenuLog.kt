package au.com.menulog

import android.app.Application
import au.com.menulog.di.*
import au.com.menulog.di.OkHttpModule

class MenuLog : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
        appComponent.inject(this)
    }

    private fun createAppComponent(): AppComponent {
        return with(DaggerAppComponent.builder()) {
            appModule(AppModule(this@MenuLog))
            okHttpModule(OkHttpModule())
            retrofitModule(RetrofitModule(BuildConfig.BASE_URL))
            picassoModule(PicassoModule())
            build()
        }
    }
}