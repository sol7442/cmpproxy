@echo off
set JARS=
set CLASSPATH=
for %%i in (..\libs\*.jar) do call jar_append.bat %%i
set CLASSPATH=%JARS%;

@echo %CLASSPATH%
java com.initech.crossweb.proxy.ProxyDaemon