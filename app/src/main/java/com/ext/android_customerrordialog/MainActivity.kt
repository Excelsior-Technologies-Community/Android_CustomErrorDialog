package com.ext.android_customerrordialog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.error_dialog.CustomDialog
import com.ext.error_dialog.DialogType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CustomDialog.Builder(this)
            .setType(DialogType.SUCCESS)
            .setTitle("Success!")
            .setMessage("Payment Completed Successfully")
            .setButtonColor(R.color.white)
            .setCancelable(true)
            .show()


    }
}