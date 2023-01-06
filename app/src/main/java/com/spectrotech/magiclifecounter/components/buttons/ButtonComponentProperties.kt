package com.spectrotech.magiclifecounter.components.buttons

import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentLabelPosition
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentSize
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentState
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentType

data class ButtonComponentProperties(
    val size: ButtonComponentSize,
    val type: ButtonComponentType,
    val state: ButtonComponentState,
    val labelPosition: ButtonComponentLabelPosition
)