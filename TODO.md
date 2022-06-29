- [ ] Config file should save on exit if it doesn't exist
- [ ] Add comments to config file

- [ ] Resolve lintian warnings:
  - [x] W: timecard-kt source: ancient-standards-version 3.9.4 (released 2012-09-19) (current is 4.5.0)
  - [x] W: timecard-kt source: debhelper-but-no-misc-depends timecard-kt
  - [x] W: timecard-kt source: no-newline-at-end debian/rules

- [x] version.py should also set the VERSION constant in App.kt



# Packaging
In order of importance
- [x] Package for Debian
- [ ] Windows installer
- [ ] Mac? Maybe through brew?
- [x] Look into packaging for snap
  - Yes I'm aware that snap packages are generally bad, but they work well with CLI applications, and flatpak does not. Until they can figure themselves out, snap packages it is
- [ ] Package for Fedora
  - [ ] Copr repo?
- [ ] Package for Arch
  - [ ] Upload to the AUR
