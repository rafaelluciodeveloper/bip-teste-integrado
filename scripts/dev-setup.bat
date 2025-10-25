@echo off
REM Development Setup Script for BIP Teste Integrado
REM This script sets up the development environment on Windows

setlocal enabledelayedexpansion

echo 🚀 Setting up BIP Teste Integrado Development Environment

REM Check if required tools are installed
echo [INFO] Checking requirements...

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed. Please install Java 17 or higher.
    exit /b 1
)

REM Check Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed. Please install Maven 3.6 or higher.
    exit /b 1
)

REM Check Node.js
node -v >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js is not installed. Please install Node.js 18 or higher.
    exit /b 1
)

REM Check npm
npm -v >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] npm is not installed. Please install npm.
    exit /b 1
)

echo [SUCCESS] All requirements are met!

REM Setup database
echo [INFO] Setting up H2 database...
if not exist "data" mkdir data
echo [SUCCESS] Database setup completed!

REM Build EJB module
echo [INFO] Building EJB module...
cd ejb-module
call mvn clean compile test
if %errorlevel% neq 0 (
    echo [ERROR] EJB module build failed
    exit /b 1
)
cd ..
echo [SUCCESS] EJB module built successfully!

REM Build Backend module
echo [INFO] Building Backend module...
cd backend-module
call mvn clean compile test
if %errorlevel% neq 0 (
    echo [ERROR] Backend module build failed
    exit /b 1
)
cd ..
echo [SUCCESS] Backend module built successfully!

REM Setup Frontend
echo [INFO] Setting up Frontend...
cd frontend
call npm install
if %errorlevel% neq 0 (
    echo [ERROR] Frontend dependencies installation failed
    exit /b 1
)
call npm run build
if %errorlevel% neq 0 (
    echo [ERROR] Frontend build failed
    exit /b 1
)
cd ..
echo [SUCCESS] Frontend setup completed!

REM Run tests
echo [INFO] Running all tests...

REM Test EJB module
echo [INFO] Testing EJB module...
cd ejb-module
call mvn test
if %errorlevel% neq 0 (
    echo [ERROR] EJB module tests failed
    exit /b 1
)
cd ..

REM Test Backend module
echo [INFO] Testing Backend module...
cd backend-module
call mvn test
if %errorlevel% neq 0 (
    echo [ERROR] Backend module tests failed
    exit /b 1
)
cd ..

REM Test Frontend
echo [INFO] Testing Frontend...
cd frontend
call npm run test -- --watchAll=false
if %errorlevel% neq 0 (
    echo [ERROR] Frontend tests failed
    exit /b 1
)
cd ..

echo [SUCCESS] All tests completed!
echo [SUCCESS] Development environment setup completed!
echo [INFO] You can now run:
echo [INFO]   - Backend: cd backend-module ^&^& mvn spring-boot:run
echo [INFO]   - Frontend: cd frontend ^&^& npm start
echo [INFO]   - Database: java -jar h2-*.jar

pause
