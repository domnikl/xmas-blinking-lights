package io.domnikl.xmas

import pigpio.*
import platform.posix.rand
import platform.posix.sleep
import platform.posix.srand

@ExperimentalUnsignedTypes
typealias Led = UInt

@ExperimentalUnsignedTypes
fun main() {
    val blinkDuration = 70
    val longBlinkDuration = 100

    println("starting ...")

    if (gpioInitialise() < 0) {
        throw RuntimeException("Could not initialise GPIO")
    }

    val leds = listOf(2.toUInt(), 3.toUInt(), 26.toUInt(), 19.toUInt(), 13.toUInt())
    val ledArray = GpioLedArray(leds)

    ledArray.switchAll(State.ON)
    srand(leds.sum())

    while (true) {
        when (rand() % 20) {
            1, 2, 3 -> {
                val led = ledArray.random()
                println("blinking on $led")
                ledArray.blink(led, blinkDuration)
            }
            5 -> {
                println("long blink all")
                ledArray.shutOffAndOnAgainSlowly(longBlinkDuration * 5)
            }
            6 -> {
                println("blink through all")
                ledArray.blinkThroughAll(longBlinkDuration)
            }
        }

        sleep(1)
    }

    // TODO: gpioTerminate()
}
