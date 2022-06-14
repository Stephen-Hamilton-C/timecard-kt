# timecard-kt
Basically a port of my [timecard-py](https://github.com/Stephen-Hamilton-C/timecard-py) Python script to Kotlin. I wanted to get some practical experience with the language and even attempt to get this properly packaged for Linux.

## Building

### Any Platform 
1. Run `./gradlew build`
2. Unzip the archive in `./build/distributions/`
3. Use `timecard-kt` for Linux or MacOS and `timecard-kt.bat` for Windows.

Executables are found in `bin`. Make sure that the `lib` folder remains on the same level as the folder containing the executable.

For example, for manually installing only for the user on Linux:

```
/
└─ home
   └─ user
      └─ .local
         ├─ bin
         │  └─ timecard-kt
         └─ lib
            └─ *.jar files...
   
```

### Ubuntu/Debian
1. Run `./gradlew debianBuild`
2. Run `sudo apt install ./build/debian/timecard-kt*.deb`
3. Run `timecard-kt help` to get started

### Windows
1. Run `./gradlew.bat createExe`
2. Move the exe file in `./build/launch4j/` to your desired location (like your User folder for example)
3. [Add the folder to your PATH](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/)
4. Open a Command Prompt or Windows Powershell
5. Run `timecard-kt.exe help`
