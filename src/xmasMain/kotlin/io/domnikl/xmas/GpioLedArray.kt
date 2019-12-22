package io.domnikl.xmas

import pigpio.PI_OUTPUT
import pigpio.gpioSetMode
import pigpio.gpioWrite
import platform.posix.sleep
import platform.posix.usleep

class GpioLedArray @ExperimentalUnsignedTypes constructor(private val gpioPins: List<Led>) {
    init {
        gpioPins.forEach {
            gpioSetMode(it, mode = PI_OUTPUT)
        }
    }

    fun random() = gpioPins.shuffled().first()

    @ExperimentalUnsignedTypes
    fun switchAll(state: State) {
        gpioPins.forEach { gpioWrite(it, state.toUInt()) }
    }

    @ExperimentalUnsignedTypes
    fun shutOffAndOnAgainSlowly(duration: Int) {
        gpioPins.shuffled().forEach {
            gpioWrite(it, State.OFF.toUInt())
            usleep(duration.toUInt() * 1000.toUInt())
        }

        sleep(3)

        gpioPins.shuffled().forEach {
            usleep(duration.toUInt() * 1000.toUInt())
            gpioWrite(it, State.ON.toUInt())
        }
    }

    @ExperimentalUnsignedTypes
    fun blinkThroughAll(duration: Int) {
        gpioPins.shuffled().forEach { blink(it, duration) }
    }

    @ExperimentalUnsignedTypes
    fun blink(led: Led, duration: Int) {
        gpioWrite(led, State.OFF.toUInt())
        usleep(duration.toUInt() * 1000.toUInt())
        gpioWrite(led, State.ON.toUInt())
    }
}
