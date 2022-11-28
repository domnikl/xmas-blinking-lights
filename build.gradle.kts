plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.7.22"
    id("org.hidetake.ssh") version "2.10.1"
}
repositories {
    mavenCentral()
    jcenter()
}
kotlin {
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    linuxArm32Hfp("xmas") {
        binaries {
            executable {
                // Change to specify fully qualified name of your application's entry point:
               entryPoint = "io.domnikl.xmas.main"
                // Specify command-line arguments, if necessary:
                runTask?.args("")

                linkerOpts("-Lsrc/lib/pigpio", "-lpigpio")
            }
        }

        compilations.getByName("main").cinterops {
            create("pigpio") {
                includeDirs("src/include/pigpio")
            }
        }
    }
}
