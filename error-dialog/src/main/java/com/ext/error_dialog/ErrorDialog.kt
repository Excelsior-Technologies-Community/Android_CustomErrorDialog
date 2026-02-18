package com.ext.error_dialog

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
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
import com.google.android.material.card.MaterialCardView

class CustomDialog private constructor(
    private val context: Context,
    private val type: DialogType,
    private val title: String,
    private val message: String,
    private val icon: Int?,

    private val positiveText: String,
    private val negativeText: String?,

    private val dialogBackgroundColor: Int?,

    private val positiveColor: Int?,
    private val negativeColor: Int?,

    private val cancelable: Boolean,

    private val onPositiveClick: (() -> Unit)?,
    private val onNegativeClick: (() -> Unit)?
) {

    fun show() {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_error, null)

        dialog.setContentView(view)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cardView = view as MaterialCardView
        dialogBackgroundColor?.let {

            val color = ContextCompat.getColor(context, it)

            // Remove tint overlay
            cardView.backgroundTintList = null

            // Force apply color in all themes
            cardView.setCardBackgroundColor(
                ColorStateList.valueOf(color)
            )
        }

        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.setCancelable(cancelable)

        // Views
        val iconView = view.findViewById<ImageView>(R.id.errorIcon)
        val titleView = view.findViewById<TextView>(R.id.errorTitle)
        val messageView = view.findViewById<TextView>(R.id.errorMessage)

        val btnPositive = view.findViewById<Button>(R.id.btnPositive)
        val btnNegative = view.findViewById<Button>(R.id.btnNegative)

        // Text
        titleView.text = title
        messageView.text = message

        // Icon default
        val defaultIcon = when (type) {
            DialogType.SUCCESS -> android.R.drawable.checkbox_on_background
            DialogType.WARNING -> android.R.drawable.ic_dialog_info
            DialogType.ERROR -> android.R.drawable.ic_delete
        }

        iconView.setImageResource(icon ?: defaultIcon)

        // Positive Button
        btnPositive.text = positiveText
        positiveColor?.let {
            btnPositive.setBackgroundColor(ContextCompat.getColor(context, it))
        }

        btnPositive.setOnClickListener {
            dialog.dismiss()
            onPositiveClick?.invoke()
        }

        // Negative Button
        if (negativeText == null) {
            btnNegative.visibility = Button.GONE
        } else {
            btnNegative.text = negativeText

            negativeColor?.let {
                btnNegative.setBackgroundColor(ContextCompat.getColor(context, it))
            }

            btnNegative.setOnClickListener {
                dialog.dismiss()
                onNegativeClick?.invoke()
            }
        }

        dialog.show()
    }

    // ✅ Builder Class
    class Builder(private val context: Context) {

        private var type: DialogType = DialogType.ERROR
        private var title: String = "Error"
        private var message: String = "Something went wrong!"
        private var icon: Int? = null

        private var positiveText: String = "OK"
        private var negativeText: String? = null

        private var positiveColor: Int? = null
        private var negativeColor: Int? = null

        private var cancelable: Boolean = false

        private var onPositiveClick: (() -> Unit)? = null
        private var onNegativeClick: (() -> Unit)? = null
        private var dialogBackgroundColor: Int? = null


        fun setType(type: DialogType) = apply { this.type = type }

        fun setTitle(title: String) = apply { this.title = title }

        fun setMessage(message: String) = apply { this.message = message }

        fun setIcon(@DrawableRes icon: Int) = apply { this.icon = icon }

        fun setDialogBackgroundColor(@ColorRes color: Int) = apply {
            this.dialogBackgroundColor = color
        }


        // ✅ Positive Button
        fun setPositiveButton(
            text: String,
            @ColorRes color: Int? = null,
            listener: (() -> Unit)? = null
        ) = apply {
            this.positiveText = text
            this.positiveColor = color
            this.onPositiveClick = listener
        }

        // ✅ Negative Button
        fun setNegativeButton(
            text: String,
            @ColorRes color: Int? = null,
            listener: (() -> Unit)? = null
        ) = apply {
            this.negativeText = text
            this.negativeColor = color
            this.onNegativeClick = listener
        }

        fun setCancelable(value: Boolean) = apply {
            this.cancelable = value
        }

        fun build(): CustomDialog {
            return CustomDialog(
                context,
                type,
                title,
                message,
                icon,
                positiveText,
                negativeText,
                positiveColor,
                negativeColor,
                dialogBackgroundColor,
                cancelable,
                onPositiveClick,
                onNegativeClick
            )
        }

        fun show() {
            build().show()
        }
    }
}
