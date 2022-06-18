;Include Modern UI
!include "MUI2.nsh"

;Basic configuration
Name "timecard-kt"
OutFile "timecard-kt_installer.exe"
Unicode True
;Default installation folder
InstallDir "$PROGRAMFILES64\timecard-kt"
;Request admin privileges for Vista/7/8/10
RequestExecutionLevel admin
!define MUI_ABORTWARNING

;Pages
!insertmacro MUI_PAGE_LICENSE "LICENSE"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES  
  
;Languages
!insertmacro MUI_LANGUAGE "English"

;Installer Sections
Section "timecard-kt" SecInstall
    SetOutPath "$INSTDIR"
    FILE LICENSE
    FILE build\launch4j\timecard-kt.exe
    SetOutPath "$INSTDIR\lib"
    FILE "build\launch4j\lib\*"
SectionEnd
Section "Add to PATH" SecPath
    ; https://nsis-dev.github.io/NSIS-Forums/html/t-335716.html
    ReadRegStr $0 HKLM "SYSTEM\CurrentControlSet\Control\Session Manager\Environment" "Path"    # read current PATH into $0
    StrCpy $0 "$0;$INSTDIR"   # append your path to the current PATH value
    WriteRegStr HKLM "SYSTEM\CurrentControlSet\Control\Session Manager\Environment" "Path" '$0' # write back the new PATH
    DetailPrint "Added $INSTDIR to system PATH"
SectionEnd

Function .onInit
  SectionSetFlags ${SecInstall} 17
FunctionEnd

;Descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${SecInstall} "Installs timecard-kt."
    !insertmacro MUI_DESCRIPTION_TEXT ${SecPath} "Adds timecard-kt to your PATH so it can be quickly accessed in command line."
!insertmacro MUI_FUNCTION_DESCRIPTION_END