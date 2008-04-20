#define version="0.7"
#define copyright="Coded by Sven Strickroth 2007-2008"

[Setup]
AppName=jBubbleBreaker
AppVerName=jBubbleBreaker {#version}
AppPublisher=Sven Strickroth
AppPublisherURL=http://jbubblebreaker.sf.net
AppSupportURL=http://jbubblebreaker.sf.net
AppUpdatesURL=http://jbubblebreaker.sf.net
AppVersion={#version}
AllowNoIcons=true
DefaultDirName={pf}\jBubbleBreaker
AppCopyright={#copyright}
PrivilegesRequired=none
OutputBaseFilename=jBubbleBreaker-install-{#version}
AllowRootDirectory=false
UsePreviousGroup=true
DisableStartupPrompt=true
OutputDir=..\
UseSetupLdr=true
DefaultGroupName=jBubbleBreaker
InternalCompressLevel=max
EnableDirDoesntExistWarning=true
UninstallDisplayName=jBubbleBreaker {#version}
UninstallDisplayIcon={app}\jbubblebreaker.ico
VersionInfoVersion={#version}
VersionInfoTextVersion={#version}
ShowLanguageDialog=yes
AppID={{084C38F2-07C2-4EC8-8337-950BCBA09957}
LicenseFile=..\COPYING

[Files]
Source: ..\jbubblebreaker-{#version}.jar; DestDir: {app}; DestName: jbubblebreaker.jar
Source: ..\source\images\jbubblebreaker.ico; DestDir: {app}
Source: ..\jbubblebreaker-src-{#version}.zip; DestDir: {app}; Components: source; DestName: jbubblebreaker-src.zip

[Icons]
Name: {group}\Uninstall jBubbleBreaker; Filename: {uninstallexe}
Name: {group}\{cm:OnTheInternet}; Filename: http://jbubblebreaker.sf.net
Name: {group}\jBubbleBreaker; Filename: {app}\jbubblebreaker.jar; WorkingDir: {app}; IconIndex: 0; IconFilename: {app}\jbubblebreaker.ico; Tasks: 
Name: {userdesktop}\jBubbleBreaker; Filename: {app}\jbubblebreaker.jar; WorkingDir: {app}; IconIndex: 0; Tasks: desktopicon; IconFilename: {app}\jbubblebreaker.ico

[Run]
Filename: {app}\jbubblebreaker.jar; Description: {cm:StartjBubbleBreaker}; Flags: nowait postinstall skipifsilent shellexec runascurrentuser; WorkingDir: {app}

[Components]
Name: jbubblebreaker; Description: {cm:MainProgram}; Flags: fixed; Types: custom typical full
Name: source; Description: {cm:InstallSource}; Types: full

[Types]
Name: typical; Description: {cm:TypicalInstallation}
Name: full; Description: {cm:FullInstallation}
Name: custom; Description: {cm:CustomInstallation}; Flags: iscustom

[Tasks]
Name: desktopicon; Description: {cm:DesktopIcon}; GroupDescription: {cm:AdditionalIcons}

[Languages]
Name: en; MessagesFile: compiler:Default.isl
Name: de; MessagesFile: compiler:Languages\German.isl

[CustomMessages]
de.TypicalInstallation=Typische Installation
en.TypicalInstallation=typical installation
de.FullInstallation=Vollständige Installation
en.FullInstallation=full installation
de.CustomInstallation=Benutzerdefinierte Installation
en.CustomInstallation=custom installation
de.OnTheInternet=jBubbleBreaker im Internet
en.OnTheInternet=jBubbleBreaker on the internet
de.StartjBubbleBreaker=jBubbleBreaker starten
en.StartjBubbleBreaker=Run jBubbleBreaker
de.DesktopIcon=&Desktopicon erstellen
en.DesktopIcon=Create &desktop icon
de.AdditionalIcons=Zusätzliche Icons:
en.AdditionalIcons=Additional icons
de.InstallSource=Quelltexte installieren
en.InstallSource=install source code
de.MainProgram=jBubbleBreaker Hauptprogramm
en.MainProgram=jBubbleBreaker main programm
de.NoJavaFound=Das Setup konnte keine Java Runtime Environment finden.
en.NoJavaFound=Setup was not able to detect an installed Java Runtime Enviroment.
de.YouNeedAtLeast=Sie benötigen mindestens die Version 1.5 oder höher um jBubbleBreaker ausführen zu können.
en.YouNeedAtLeast=You must have installed at least Java Runtime 1.5 or higher to run jBubbleBreaker.
de.GetJava=Sie können die aktuelle Version der Java Runtime Environment (Java SE) unter http://java.sun.com/getjava erhalten.
en.GetJava=You can obtain the latest Java Runtime (Java SE) from http://java.sun.com/getjava.
de.JavaTooOld=Das Setup konnte keine Java Runtime Environment mit mindestens Version 1.5 finden.
en.JavaTooOld=Setup was not able to detect at least Java Runtime 1.5.
de.InstallAnyway=Installation ohne die Java Runtime Environment fortsetzen?
en.InstallAnyway=Install without the Java Runtime Environment?

[Code]
//* Getting Java version from registry *//
function getJavaVersion(): String;
var
     javaVersion: String;
begin
     javaVersion := '';
     RegQueryStringValue(HKLM, 'SOFTWARE\JavaSoft\Java Runtime Environment', 'CurrentVersion', javaVersion);
     GetVersionNumbersString(javaVersion, javaVersion);
     if Length( javaVersion ) = 0 then begin
         RegQueryStringValue(HKLM, 'SOFTWARE\JavaSoft\Java Development Kit', 'CurrentVersion', javaVersion);
         GetVersionNumbersString(javaVersion, javaVersion);
     end
     Result := javaVersion;
end;

//* Called on setup startup //*
function InitializeSetup(): Boolean;
begin
     Result := true;
     if Length( getJavaVersion() ) = 0 then begin
         //* No Java detected *//
         if MsgBox(CustomMessage('NoJavaFound')+#13+CustomMessage('YouNeedAtLeast')+#13+CustomMessage('GetJava')+#13+CustomMessage('InstallAnyway'), mbError, MB_YESNO or MB_DEFBUTTON2) = IDNO then
             result := false;
         end
     else begin
         //* Java version lower than 1.5 detected *//
         if (getJavaVersion()) < '1.5' then begin
             if MsgBox(CustomMessage('JavaTooOld')+#13+CustomMessage('YouNeedAtLeast')+#13+CustomMessage('GetJava')+#13+CustomMessage('InstallAnyway'), mbError, MB_YESNO or MB_DEFBUTTON2) = IDNO then
                 result := false;
             end;
         end;
     end;
end.
