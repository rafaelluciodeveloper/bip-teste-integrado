# CI/CD Pipeline Documentation

## Overview

Este documento descreve o pipeline de CI/CD implementado para o projeto BIP Teste Integrado.

## Pipeline Structure

### 1. Test Backend
- **Trigger**: Push/PR para main, develop, feature/*
- **Runtime**: Ubuntu Latest
- **Steps**:
  - Checkout code
  - Setup JDK 17
  - Cache Maven dependencies
  - Build and test backend-module
  - Build and test ejb-module

### 2. Test Frontend
- **Trigger**: Push/PR para main, develop, feature/*
- **Runtime**: Ubuntu Latest
- **Steps**:
  - Checkout code
  - Setup Node.js 20
  - Install dependencies
  - Lint code
  - Run tests with coverage
  - Build application

### 3. Integration Tests
- **Dependencies**: test-backend, test-frontend
- **Steps**:
  - Build all modules
  - Run integration tests
  - Validate end-to-end functionality

### 4. Security Scan
- **Dependencies**: test-backend, test-frontend
- **Tools**: OWASP Dependency Check
- **Output**: Security report artifact

### 5. Build Artifacts
- **Dependencies**: All test jobs
- **Outputs**:
  - EJB JAR file
  - Backend JAR file
  - Frontend build files

### 6. Quality Gate
- **Dependencies**: test-backend, test-frontend, security-scan
- **Purpose**: Final validation before merge

## Local Development

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 18+
- npm

### Quick Start

#### Linux/Mac
```bash
./scripts/dev-setup.sh
```

#### Windows
```cmd
scripts\dev-setup.bat
```

### Manual Setup

1. **Database Setup**
   ```bash
   # H2 in-memory database is automatically configured
   # No additional setup required
   ```

2. **Backend Development**
   ```bash
   cd backend-module
   mvn spring-boot:run
   ```

3. **Frontend Development**
   ```bash
   cd frontend
   npm start
   ```

## Docker Development

### Using Docker Compose
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Individual Services
```bash
# Build and run backend
docker build -t bip-backend ./backend-module
docker run -p 8080:8080 bip-backend

# Build and run frontend
docker build -t bip-frontend ./frontend
docker run -p 4200:4200 bip-frontend
```

## Testing

### Unit Tests
```bash
# Backend tests
cd backend-module
mvn test

# EJB tests
cd ejb-module
mvn test

# Frontend tests
cd frontend
npm test
```

### Integration Tests
```bash
# Run all integration tests
npm run test:integration
```

### Coverage Reports
```bash
# Backend coverage
cd backend-module
mvn jacoco:report

# Frontend coverage
cd frontend
npm run test -- --coverage
```

## Security

### Dependency Scanning
- OWASP Dependency Check
- Automated security scanning
- Vulnerability reports

### Security Headers
- X-Frame-Options
- X-Content-Type-Options
- X-XSS-Protection
- Referrer-Policy

## Monitoring

### Health Checks
- Backend: `http://localhost:8080/actuator/health`
- Frontend: `http://localhost:4200/health`

### Metrics
- Spring Boot Actuator
- Application metrics
- Performance monitoring

## Deployment

### Artifacts
- EJB JAR: `ejb-module/target/*.jar`
- Backend JAR: `backend-module/target/*.jar`
- Frontend Build: `frontend/dist/`

### Environment Variables
See `config/environment.properties` for configuration options.

## Troubleshooting

### Common Issues

1. **Port Conflicts**
   - Backend: 8080
   - Frontend: 4200
   - Database: 8082

2. **Dependency Issues**
   - Maven: `mvn clean install`
   - npm: `npm ci`

3. **Database Connection**
   - H2 in-memory database is automatically configured
   - No external database setup required

### Logs
- Backend: `backend-module/logs/`
- Frontend: Browser console

## Best Practices

1. **Code Quality**
   - Run tests before commit
   - Use linting tools
   - Follow coding standards

2. **Security**
   - Regular dependency updates
   - Security scanning
   - Secure configuration

3. **Performance**
   - Monitor resource usage
   - Optimize build times
   - Cache dependencies

## Support

For issues and questions:
- Check logs for error details
- Verify environment setup
- Review configuration files
- Consult documentation
