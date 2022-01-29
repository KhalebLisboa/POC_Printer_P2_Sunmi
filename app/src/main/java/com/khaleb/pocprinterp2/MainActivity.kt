package com.khaleb.pocprinterp2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.sunmi.peripheral.printer.InnerPrinterCallback
import woyou.aidlservice.jiuiv5.ICallback
import woyou.aidlservice.jiuiv5.IWoyouService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val connService = object : ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val woyouService = IWoyouService.Stub.asInterface(p1)

                val callback = object  : ICallback{
                    override fun asBinder(): IBinder {
                        return p1!!
                    }

                    override fun onRunResult(isSuccess: Boolean) {

                    }

                    override fun onReturnString(result: String?) {
                    }

                    override fun onRaiseException(code: Int, msg: String?) {
                    }

                    override fun onPrintResult(code: Int, msg: String?) {
                    }
                }
                woyouService.printerInit(callback)
                woyouService.printText("Teste de impressão", callback)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
            }

        }

        val btn : Button = findViewById(R.id.btn)

        btn.setOnClickListener {
            val intent = Intent()
            intent.setPackage("woyou.aidlservice.jiuiv5")
            intent.action = "woyou.aidlservice.jiuiv5.IWoyouService"
            bindService(intent, connService, Context.BIND_AUTO_CREATE)
        }

    }

}