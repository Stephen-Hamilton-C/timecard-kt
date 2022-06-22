# timecard-kt
Basically a port of my [timecard-py](https://github.com/Stephen-Hamilton-C/timecard-py) Python script to Kotlin. I wanted to get some practical experience with the language and even attempt to get this properly packaged for Linux.

## Table of Contents
- [timecard-kt](#timecard-kt)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
    - [Linux Snap (Any distro)](#linux-snap-any-distro)
    - [Ubuntu](#ubuntu)
    - [Windows](#windows)
  - [Install from Source](#install-from-source)
    - [Any Platform](#any-platform)
    - [Linux Snap](#linux-snap)
    - [Ubuntu/Debian](#ubuntudebian)
    - [Windows](#windows-1)

## Installation

### Linux Snap (Any distro)
1. Run `snap install timecard-kt`

### Ubuntu
1. Add my launchpad repo: `sudo add-apt-repository ppa:stephen-hamilton-c/ppa`
   - If you want developer snapshots (new features faster but less stable), use `ppa:stephe-hamilton-c/snapshots` instead
2. Run `sudo apt update`
3. Run `sudo apt install timecard-kt`

### Windows
1. Download the Windows zip folder from [releases](https://github.com/Stephen-Hamilton-C/timecard-kt/releases/latest).
2. Right click the zip folder and click Extract All.
3. Move `lib` and `timecard-kt.exe` to desired location. (Like `C:\Program Files\timecard-kt`)
4. Add parent folder to your PATH
*Installer coming soon*

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

### Linux Snap
1. Run `./gradlew snapBuild`
2. Run `snap install ./deploy/snap/timecard-kt_*.snap`
3. Run `timecard-kt help` to get started

### Ubuntu/Debian
1. Run `./gradlew debianBuild`
2. Run `sudo apt install ./build/debian/timecard-kt*.deb`
3. Run `timecard-kt help` to get started

### Windows
1. Run `.\gradlew.bat createExe`
2. Move the exe and lib file in `.\build\launch4j\` to your desired location (like your User folder for example)
3. [Add the folder to your PATH](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/)
4. Open a Command Prompt or Windows Powershell
5. Run `timecard-kt.exe help`
