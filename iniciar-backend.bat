@echo off
cd /d "%~dp0"
title Sanaesencia Backend - Spring Boot
echo ========================================================
echo       Iniciando Microservicio Sanaesencia Backend
echo ========================================================
echo.

set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "MAVEN_BIN=C:\Program Files\JetBrains\IntelliJ IDEA 2026.2.2\plugins\maven-plugin\lib\maven3\bin\mvn.cmd"

echo [INFO] Directorio: %cd%
echo [INFO] Java: %JAVA_HOME%
echo.

if not exist "%MAVEN_BIN%" (
    echo [ERROR] No se encontro Maven en: %MAVEN_BIN%
    pause
    exit /b 1
)

echo [INFO] Iniciando servidor Spring Boot en puerto 8080...
echo [INFO] Base de Datos: Oracle Autonomous DB sanaescencia_high
echo.

call "%MAVEN_BIN%" spring-boot:run

echo.
echo Presiona cualquier tecla para salir...
pause
