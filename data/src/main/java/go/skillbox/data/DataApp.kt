package go.skillbox.data

import android.app.Application
import android.content.Context

open class DataApp : Application() {

    companion object {
        private var appContext: Context? = null
        fun getContext() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}