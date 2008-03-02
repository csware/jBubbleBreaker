@echo off
rem based on http://www.vincenzo.net/isxkb/index.php?title=Obtaining_Inno_Setup%27s_installation_path
SET CompilerExe=
FOR /F "eol=; tokens=1,2* delims=	" %%a IN ('REG QUERY "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\Inno Setup 5_is1" /v InstallLocation') DO (
	SET CompilerExe=%%c
)
SET CompilerExe="%CompilerExe%ISCC.exe"
%CompilerExe% %1
