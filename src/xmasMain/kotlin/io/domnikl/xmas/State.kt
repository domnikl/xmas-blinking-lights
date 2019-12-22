package io.domnikl.xmas

import pigpio.PI_HIGH
import pigpio.PI_LOW

@ExperimentalUnsignedTypes
enum class State(private val state: UInt) {
    OFF(PI_LOW.toUInt()),
    ON(PI_HIGH.toUInt());

    fun toUInt() = state
}
