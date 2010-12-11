xgettext -ktrc:1c,2 -ktrnc:1c,2,3 -ktr -kmarktr -ktrn:1,2 -o po/keys.pot source\org\jbubblebreaker\*.java

msgmerge -U po/en.po po/keys.pot
msgmerge -U po/de.po po/keys.pot
msgmerge -U po/gl.po po/keys.pot

pause
