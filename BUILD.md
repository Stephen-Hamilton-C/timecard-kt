# Build from Source

## Dependencies
- Download and install [Python 3](https://www.python.org/downloads/)
- Download and install [JDK 8 or higher](https://www.oracle.com/java/technologies/downloads/#java17)

## Any Platform
1. Run `./gradlew build`
2. Unzip the archive in `./build/distributions/`
3. Use `timecard-kt` for Linux or macOS and `timecard-kt.bat` for Windows.

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

## Linux Snap
1. Run `sudo snap install snapcraft multipass`
2. Run `./gradlew snapBuild`
3. Run `snap install ./deploy/snap/timecard-kt_*.snap`
4. Run `timecard-kt help` to get started

## Debian/Ubuntu
1. Run `sudo apt install build-essential devscripts debhelper`
2. Run `./gradlew debianBuild`
3. Run `sudo apt install ./build/debian/timecard-kt*.deb`
4. Run `timecard-kt help` to get started

## Windows

### Installer
1. Run `.\gradlew.bat createInstaller`
2. Run the installer in `.\build\distributions\`

### Manual
1. Run `.\gradlew.bat createExe`
2. Move the exe and lib file in `.\build\launch4j` to your desired location (like your User folder for example)
3. [Add the folder to your PATH](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/)
4. Open a Command Prompt or Windows Powershell
5. Run `timecard-kt.exe help`
