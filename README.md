# timecard-kt
Basically a port of my [timecard-py](https://github.com/Stephen-Hamilton-C/timecard-py) Python script to Kotlin. I wanted to get some practical experience with the language and even attempt to get this properly packaged for Linux.

## Table of Contents
- [timecard-kt](#timecard-kt)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
    - [Ubuntu](#ubuntu)
    - [Windows](#windows)
  - [Install from Source](#install-from-source)
    - [Any Platform](#any-platform)
    - [Ubuntu/Debian](#ubuntudebian)
    - [Windows](#windows-1)
      - [Installer](#installer)
      - [Manual](#manual)
  - [Files](#files)
    - [Windows](#windows-2)
    - [Linux](#linux)

## Installation

### Ubuntu
1. Add my launchpad repo: `sudo add-apt-repository ppa:stephen-hamilton-c/ppa`
   - If you want developer snapshots (new features faster but less stable), use `ppa:stephe-hamilton-c/snapshots` instead
2. Run `sudo apt update`
3. Run `sudo apt install timecard-kt`

### Windows
1. Download the Windows installer from [releases](https://github.com/Stephen-Hamilton-C/timecard-kt/releases/latest).
2. Run the installer.

## Install from Source

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

#### Installer
1. Run `.\gradlew.bat createInstaller`
2. Run the installer in `.\build\distributions\`

#### Manual
1. Run `.\gradlew.bat createExe`
2. Find the executable and `lib` folder in `.\build\launch4j`

## Files

### Windows
- Timecard logs and `config.yml` are found in `%LocalAppData%\stephen-hamilton-c\timecard-kt\`

### Linux
<!-- TODO: Need to confirm this -->
- Timecard logs are found in `~/.local/share/timecard-kt/`
- `config.yml` is found in `~/.config/timecard-kt/`