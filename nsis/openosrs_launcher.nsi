!define APP_NAME "Elder Launcher"
!define APP_PUBLISHER "Elder"
!define APP_VERSION "3.0.0"
!define APP_URL "https://openosrs.com/"
!define APP_EXE "OpenOSRS.exe"
!define INSTALL_DIR "$LOCALAPPDATA\OpenOSRS"

!include "MUI2.nsh"

# Match icons/images exactly
!define MUI_ICON "openosrs.ico"
!define MUI_UNICON "openosrs.ico"
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_RIGHT
!define MUI_HEADERIMAGE_BITMAP "innosetup\openosrs_small.bmp"
!define MUI_WELCOMEFINISHPAGE_BITMAP "innosetup\openosrs_small.bmp"

# Installer meta
Name "${APP_NAME}"
OutFile "OpenOSRSSetup.exe"
InstallDir "${INSTALL_DIR}"
InstallDirRegKey HKCU "Software\${APP_NAME}" ""
RequestExecutionLevel user

# Pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_LANGUAGE "English"

# ==================================
# VC++ Redist Detection
# ==================================
Var VCREDIST_NEEDED

Function CheckVCRedist
  StrCpy $0 "{0D3E9E15-DE7A-300B-96F1-B4AF12B96488}"
  System::Call 'msi::MsiQueryProductStateA(t r0) i .r1'
  StrCmp $1 "5" 0 not_installed
  StrCpy $0 "{BC958BD2-5DAC-3862-BB1A-C1BE0790438D}"
  System::Call 'msi::MsiQueryProductStateA(t r0) i .r1'
  StrCmp $1 "5" installed not_installed

  installed:
    StrCpy $VCREDIST_NEEDED 0
    Return

  not_installed:
    StrCpy $VCREDIST_NEEDED 1
FunctionEnd

# ======================
# Install Section
# ======================
Section "Install"
  SetOutPath "$INSTDIR"

  File "native-win64\OpenOSRS.exe"
  File "native-win64\OpenOSRS-shaded.jar"
  File "native-win64\config.json"
  File /r "native-win64\jre\*"

  File "vcredist_x64.exe"
  Call CheckVCRedist
  StrCmp $VCREDIST_NEEDED 1 +2 0
    ExecWait '"$INSTDIR\vcredist_x64.exe" /install /quiet /norestart'

  CreateDirectory "$SMPROGRAMS\OpenOSRS"
  CreateShortcut "$SMPROGRAMS\OpenOSRS\OpenOSRS.lnk" "$INSTDIR\${APP_EXE}"
  CreateShortcut "$DESKTOP\OpenOSRS.lnk" "$INSTDIR\${APP_EXE}"

  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "DisplayName" "Elder Launcher"
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "Publisher" "${APP_PUBLISHER}"
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "DisplayVersion" "${APP_VERSION}"
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "URLInfoAbout" "${APP_URL}"
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "DisplayIcon" "$INSTDIR\${APP_EXE}"
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS" "UninstallString" "$INSTDIR\uninstall.exe"

  WriteUninstaller "$INSTDIR\uninstall.exe"
SectionEnd

# ======================
# Uninstall Section
# ======================
Section "Uninstall"
  Delete "$INSTDIR\OpenOSRS.exe"
  Delete "$INSTDIR\OpenOSRS-shaded.jar"
  Delete "$INSTDIR\config.json"
  RMDir /r "$INSTDIR\jre"

  Delete "$DESKTOP\OpenOSRS.lnk"
  Delete "$SMPROGRAMS\OpenOSRS\OpenOSRS.lnk"
  RMDir "$SMPROGRAMS\OpenOSRS"

  DeleteRegKey HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\OpenOSRS"
  DeleteRegKey HKCU "Software\OpenOSRS"

  ; Extra cleanup (same as [UninstallDelete] in Inno Setup)
  RMDir /r "$PROFILE\.openosrs\repository2"

  RMDir /r "$INSTDIR"
SectionEnd
