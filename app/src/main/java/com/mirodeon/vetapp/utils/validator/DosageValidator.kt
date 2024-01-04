package com.mirodeon.vetapp.utils.validator

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageButton
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mirodeon.vetapp.room.entity.Dosage
import com.mirodeon.vetapp.utils.extension.forEachUntil

class DosageValidator(
    private val inputs: Map<DosageProperty, Pair<TextInputLayout?, TextInputEditText?>>,
    private val addButton: ImageButton?,
    private val validIcon: Drawable?,
    private val colorValid: Int,
    private val colorInvalid: Int,
    private val addDosage: (dosage: Dosage) -> Unit
) {

    init {
        inputs.forEach { (property, input) ->
            addAfterTextChanged(
                { validByProperty(property) },
                input.first,
                input.second,
                property.error
            )
        }

        addButton?.setOnClickListener {
            if (dosageValidity() == DosageValidity.VALID) {
                addDosage(getDosage())
                Log.d("Validator", "Click")
            }
        }
    }

    private fun getDosage(): Dosage {
        return Dosage(
            name =
            inputs[DosageProperty.NAME]?.second?.text?.toString() ?: "",
            concentration =
            inputs[DosageProperty.CONCENTRATION]?.second?.text?.toString()?.toIntOrNull() ?: 0,
            activeIngredient =
            inputs[DosageProperty.INGREDIENT]?.second?.text?.toString()?.toIntOrNull() ?: 0,
            interval =
            inputs[DosageProperty.INTERVAL]?.second?.text?.toString()?.toIntOrNull(),
            number =
            inputs[DosageProperty.NUMBER]?.second?.text?.toString()?.toIntOrNull(),
            withdrawal =
            inputs[DosageProperty.WITHDRAWAL]?.second?.text?.toString()?.toIntOrNull()
        )
    }

    private fun validByProperty(property: DosageProperty): Boolean {
        return when (property) {
            DosageProperty.NAME -> isNeededStringValid(property)

            DosageProperty.CONCENTRATION,
            DosageProperty.INGREDIENT -> isNeededIntValid(property)

            DosageProperty.NUMBER,
            DosageProperty.INTERVAL,
            DosageProperty.WITHDRAWAL -> isNonNeededIntValid(property)
        }
    }

    private fun dosageValidity(): DosageValidity {
        var validity = false
        inputs.forEachUntil {
            validity = validByProperty(it.key)
            !validity
        }
        return if (validity) {
            DosageValidity.VALID
        } else {
            DosageValidity.ERROR
        }
    }

    private fun toggleButtonValidity() {
        val validity = dosageValidity()
        addButton?.isEnabled = validity == DosageValidity.VALID
    }

    private fun toggleInputValidity(
        isValid: Boolean,
        layout: TextInputLayout?,
        input: TextInputEditText?,
        errorText: String
    ) {
        Log.d("Validator", "toggle input")
        if (isValid) {
            layout?.isErrorEnabled = false
            input?.setTextColor(colorValid)
            layout?.endIconDrawable = validIcon
            layout?.endIconMode = TextInputLayout.END_ICON_CUSTOM
            layout?.setEndIconTintList(ColorStateList.valueOf(colorValid))
        } else {
            layout?.error = errorText
            layout?.isErrorEnabled = true
            input?.setTextColor(colorInvalid)
            layout?.endIconDrawable = null
        }
    }

    private fun addAfterTextChanged(
        validator: () -> Boolean,
        layout: TextInputLayout?,
        input: TextInputEditText?,
        errorText: String
    ) {
        input?.doAfterTextChanged {
            Log.d("Validator", "after text")
            toggleButtonValidity()
            toggleInputValidity(validator(), layout, input, errorText)
        }
    }

    private fun isNeededStringValid(property: DosageProperty): Boolean {
        return !inputs[property]?.second?.text?.toString().isNullOrBlank()
    }

    private fun isNeededIntValid(property: DosageProperty): Boolean {
        val value = inputs[property]?.second?.text?.toString()
        return !value.isNullOrBlank() && value.toIntOrNull() != null && value.toIntOrNull() != 0
    }

    private fun isNonNeededIntValid(property: DosageProperty): Boolean {
        val value = inputs[property]?.second?.text?.toString()
        return value.isNullOrBlank() || value.toIntOrNull() != null
    }

    enum class DosageValidity {
        VALID,
        ERROR
    }

    enum class DosageProperty(val error: String) {
        NAME("Please enter a valid name."),
        CONCENTRATION("Please enter a valid concentration."),
        INGREDIENT("Please enter a valid value of needed active ingredient."),
        NUMBER("Please enter a valid number or leave the field blank if the information is irrelevant."),
        INTERVAL("Please enter a valid interval or leave the field blank if the information is irrelevant."),
        WITHDRAWAL("Please enter a valid withdrawal period or leave the field blank if the information is irrelevant.")
    }
}