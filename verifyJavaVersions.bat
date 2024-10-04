#!/bin/bash
set JAVA_HOME=%JAVA8_HOME%
set RUNTIME_VERSION=4.6.3
set MUNIT_JVM=%JAVA17_HOME%\bin\java.exe
call mvn clean
call mkdir target
call mvn verify -DruntimeVersion=%RUNTIME_VERSION% -Dmunit.jvm="%MUNIT_JVM%" -Dmtf.javaopts="--illegal-access=deny"

if %ERRORLEVEL% NEQ 0 (
   echo verification failed for runtime version %RUNTIME_VERSION%
   exit /b %errorlevel%
)
set MUNIT_JVM=%JAVA8_HOME%\bin\java.exe
call mvn clean
call mkdir target
call mvn verify -DruntimeVersion=%RUNTIME_VERSION% -Dmunit.jvm="%MUNIT_JVM%" -Dmtf.javaopts="--illegal-access=deny"

if %ERRORLEVEL% NEQ 0 (
   echo verification failed for runtime version %RUNTIME_VERSION%
   exit /b %errorlevel%
)

set MUNIT_JVM=%JAVA11_HOME%\bin\java.exe
call mvn clean
call mkdir target
call mvn verify -DruntimeVersion=%RUNTIME_VERSION% -Dmunit.jvm="%MUNIT_JVM%" -Dmtf.javaopts="--illegal-access=deny"

if %ERRORLEVEL% NEQ 0 (
   echo verification failed for runtime version %RUNTIME_VERSION%
   exit /b %errorlevel%
)
echo verification completed successfully
