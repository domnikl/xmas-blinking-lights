package io.domnikl.xmas

import pigpio.*
import platform.posix.rand
import platform.posix.sleep
import platform.posix.srand

@ExperimentalUnsignedTypes
typealias Led = UInt

@ExperimentalUnsignedTypes
fun main() {
    println("starting ...")

    if (gpioInitialise() < 0) {
        throw RuntimeException("Could not initialise GPIO")
    }

    val leds = listOf(2.toUInt(), 3.toUInt(), 26.toUInt(), 19.toUInt(), 13.toUInt())
    val ledArray = GpioLedArray(leds)

    ledArray.switchAll(State.ON)
    srand(leds.sum())

    while (true) {
        if (rand() % 20 == 1) {
            println("switch on and off again")
            ledArray.switchOffAndOnAgain()
        }

        sleep(1)
    }

    // TODO: gpioTerminate()
}
