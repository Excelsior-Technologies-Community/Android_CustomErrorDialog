package com.ext.error_dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class CustomDialog private constructor(
    private val context: Context,
    private val type: DialogType,
    private val title: String,
    private val message: String,
    private val icon: Int?,
    private val buttonText: String,
    private val buttonColor: Int?,
    private val cancelable: Boolean,
    private val onDismiss: (() -> Unit)?
) {

    fun show() {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_error, null)

        dialog.setContentView(view)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // ✅ Responsive width (90%)
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.setCancelable(cancelable)

        // Views
        val iconView = view.findViewById<ImageView>(R.id.errorIcon)
        val titleView = view.findViewById<TextView>(R.id.errorTitle)
        val messageView = view.findViewById<TextView>(R.id.errorMessage)
        val button = view.findViewById<Button>(R.id.btnOk)

        // Text
        titleView.text = title
        messageView.text = message
        button.text = buttonText

        // Default icon by type
        val defaultIcon = when (type) {
            DialogType.SUCCESS -> android.R.drawable.checkbox_on_background
            DialogType.WARNING -> android.R.drawable.ic_dialog_info
            DialogType.ERROR -> android.R.drawable.ic_delete
        }

        iconView.setImageResource(icon ?: defaultIcon)

        // Button color
        buttonColor?.let {
            button.setBackgroundColor(ContextCompat.getColor(context, it))
        }

        button.setOnClickListener {
            dialog.dismiss()
            onDismiss?.invoke()
        }

        dialog.show()
    }

    // ✅ Builder Class
    class Builder(private val context: Context) {

        private var type: DialogType = DialogType.ERROR
        private var title: String = "Error"
        private var message: String = "Something went wrong!"
        private var icon: Int? = null
        private var buttonText: String = "OK"
        private var buttonColor: Int? = null
        private var cancelable: Boolean = false
        private var onDismiss: (() -> Unit)? = null

        fun setType(type: DialogType) = apply {
            this.type = type
        }

        fun setTitle(title: String) = apply {
            this.title = title
        }

        fun setMessage(message: String) = apply {
            this.message = message
        }

        fun setIcon(@DrawableRes icon: Int) = apply {
            this.icon = icon
        }

        fun setButtonText(text: String) = apply {
            this.buttonText = text
        }

        fun setButtonColor(@ColorRes color: Int) = apply {
            this.buttonColor = color
        }

        fun setCancelable(value: Boolean) = apply {
            this.cancelable = value
        }

        fun setOnDismiss(listener: () -> Unit) = apply {
            this.onDismiss = listener
        }

        fun build(): CustomDialog {
            return CustomDialog(
                context,
                type,
                title,
                message,
                icon,
                buttonText,
                buttonColor,
                cancelable,
                onDismiss
            )
        }

        fun show() {
            build().show()
        }
    }
}
