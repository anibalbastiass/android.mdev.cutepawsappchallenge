package com.anibalbastias.android.cutepaws.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.anibalbastias.android.cutepaws.base.view.BaseModuleFragment
import com.anibalbastias.android.cutepaws.presentation.component.ApplicationComponent
import com.anibalbastias.android.cutepaws.presentation.component.DaggerApplicationComponent
import com.anibalbastias.android.cutepaws.presentation.module.ApplicationModule

var context: CutePawsApplication? = null
fun getAppContext(): CutePawsApplication {
    return context!!
}

/**
 * Created by anibalbastias on 2019-11-25.
 */

class CutePawsApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
        var applicationComponent: ApplicationComponent? = null
        var isTest: Boolean? = false
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        context = this
        appContext = this
    }

}

private fun buildDagger(context: Context): ApplicationComponent {
    if (CutePawsApplication.applicationComponent == null) {
        CutePawsApplication.applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(context as CutePawsApplication))
            .build()
    }
    return CutePawsApplication.applicationComponent!!
}

fun Context.appComponent(): ApplicationComponent {
    return buildDagger(this.applicationContext)
}

fun Fragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}

fun BaseModuleFragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}