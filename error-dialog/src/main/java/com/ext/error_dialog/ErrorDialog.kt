package com.ext.error_dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class ErrorDialog(private val context: Context) {

    fun show(
        title: String = "Error",
        message: String = "Something went wrong!",
        buttonText: String = "OK",
        onDismiss: (() -> Unit)? = null
    ) {

        val dialog = Dialog(context)

        // Remove default dialog title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_error, null)

        dialog.setContentView(view)

        // ✅ Transparent background
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // ✅ Set proper width
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(false)

        val titleView = view.findViewById<TextView>(R.id.errorTitle)
        val messageView = view.findViewById<TextView>(R.id.errorMessage)
        val button = view.findViewById<Button>(R.id.btnOk)

        titleView.text = title
        messageView.text = message
        button.text = buttonText

        button.setOnClickListener {
            dialog.dismiss()
            onDismiss?.invoke()
        }

        dialog.show()
    }
}
