# timecard-kt
Basically a port of my [timecard-py](https://github.com/Stephen-Hamilton-C/timecard-py) Python script to Kotlin. I wanted to get some practical experience with the language and even attempt to get this properly packaged for Linux.

## Building

### Any Platform 
Run `./gradlew build` and unzip the archive in `./build/distributions/`.
Use `timecard-kt` for Linux or MacOS and `timecard-kt.bat` for Windows.

Executables are found in `bin`. Make sure that the `lib` folder remains on the same level as the folder containing the executable.

### Windows Only
Run `./gradlew.bat createExe` and find the exe file in `./build/launch4j/`
