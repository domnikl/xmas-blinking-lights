package io.domnikl.xmas

import pigpio.PI_OUTPUT
import pigpio.gpioSetMode
import pigpio.gpioWrite
import platform.posix.rand
import platform.posix.sleep
import platform.posix.usleep

class GpioLedArray @ExperimentalUnsignedTypes constructor(private val gpioPins: List<Led>) {
    init {
        gpioPins.forEach {
            gpioSetMode(it, mode = PI_OUTPUT)
        }
    }

    @ExperimentalUnsignedTypes
    fun switchAll(state: State) {
        gpioPins.forEach { gpioWrite(it, state.toUInt()) }
    }

    @ExperimentalUnsignedTypes
    fun switchOffAndOnAgain() {
        val count = (rand() % gpioPins.size) + 1
        val sleepSeconds = (rand() % 2) + 1
        val toSwitchOff = gpioPins.shuffled().take(count)

        toSwitchOff.forEach {
            gpioWrite(it, State.OFF.toUInt())

            val sleeping = (rand() % 1000) + 80
            usleep(sleeping.toUInt() * 1000.toUInt())
        }

        sleep(sleepSeconds.toUInt())

        toSwitchOff.shuffled().forEach {
            val sleeping = (rand() % 1000) + 80
            usleep(sleeping.toUInt() * 1000.toUInt())

            gpioWrite(it, State.ON.toUInt())
        }
    }
}
