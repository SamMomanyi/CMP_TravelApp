package com.codewithfk.travenor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.codewithfk.travenor.di.appModule
import com.codewithfk.travenor.payments.PaymentSheetHolder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        PaymentSheetHolder.instance = PaymentSheetHolder(this)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            App()
        }
    }

    fun resetData(){
        applicationContext.cacheDir.deleteRecursively()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}