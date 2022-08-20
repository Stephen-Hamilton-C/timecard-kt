# Changelog <!-- omit in toc -->
All notable changes to timecard-kt will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
timecard-kt uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# Versions <!-- omit in toc -->

- [1.2.0 - 2022-08-20](#120---2022-08-20)
- [1.1.0 - 2022-07-08](#110---2022-07-08)
- [1.0.1 - 2022-06-22](#101---2022-06-22)
- [1.0.0 - 2022-06-16](#100---2022-06-16)

# 1.2.0 - 2022-08-20

## Added
- 12 hour input (e.g. can clock out at 17:31 by running `timecard-kt out 5:31 PM`)

## Changed
- Config file now saves defaults on exit 

## Fixed
- Crash if unexpected files were in the data directory

# 1.1.0 - 2022-07-08

## Added
- Color config. Turns text colors on or off. Defaults to off on Windows.

## Changed
- Config command feedback to be helpful.
- "Unknown command" message is now colored like most error messages.

# 1.0.1 - 2022-06-22

## Fixed <!-- omit in toc -->
- Edge cases where the open command could fail
- Config and data directories now respect XDG_CONFIG_HOME and XDG_DATA_HOME environment variables

# 1.0.0 - 2022-06-16

- Initial release.
