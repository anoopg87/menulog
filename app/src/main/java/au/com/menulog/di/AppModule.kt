package au.com.menulog.di

import android.content.Context
import au.com.menulog.base.DefaultRXCallManager
import au.com.menulog.base.RXCallManager
import dagger.Module
import dagger.Provides

@Module
class AppModule(private var ctx: Context) {

    @Provides
    @AppScope
    fun provideContext(): Context = this.ctx


    @Provides
    @AppScope
    fun provideRXCallManager(): RXCallManager= DefaultRXCallManager()




}