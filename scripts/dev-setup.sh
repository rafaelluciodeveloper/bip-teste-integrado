#!/bin/bash

# Development Setup Script for BIP Teste Integrado
# This script sets up the development environment

set -e

echo "🚀 Setting up BIP Teste Integrado Development Environment"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if required tools are installed
check_requirements() {
    print_status "Checking requirements..."
    
    # Check Java
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed. Please install Java 17 or higher."
        exit 1
    fi
    
    # Check Maven
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed. Please install Maven 3.6 or higher."
        exit 1
    fi
    
    # Check Node.js
    if ! command -v node &> /dev/null; then
        print_error "Node.js is not installed. Please install Node.js 18 or higher."
        exit 1
    fi
    
    # Check npm
    if ! command -v npm &> /dev/null; then
        print_error "npm is not installed. Please install npm."
        exit 1
    fi
    
    print_success "All requirements are met!"
}

# Setup database
setup_database() {
    print_status "Setting up H2 database..."
    
    # Create data directory
    mkdir -p data
    
    # Initialize database schema
    if [ -f "db/schema.sql" ]; then
        print_status "Database schema found"
    else
        print_warning "Database schema not found. Please ensure db/schema.sql exists."
    fi
    
    print_success "Database setup completed!"
}

# Build EJB module
build_ejb() {
    print_status "Building EJB module..."
    
    cd ejb-module
    mvn clean compile test
    cd ..
    
    print_success "EJB module built successfully!"
}

# Build Backend module
build_backend() {
    print_status "Building Backend module..."
    
    cd backend-module
    mvn clean compile test
    cd ..
    
    print_success "Backend module built successfully!"
}

# Setup Frontend
setup_frontend() {
    print_status "Setting up Frontend..."
    
    cd frontend
    
    # Install dependencies
    npm install
    
    # Build frontend
    npm run build
    
    cd ..
    
    print_success "Frontend setup completed!"
}

# Run tests
run_tests() {
    print_status "Running all tests..."
    
    # Test EJB module
    print_status "Testing EJB module..."
    cd ejb-module
    mvn test
    cd ..
    
    # Test Backend module
    print_status "Testing Backend module..."
    cd backend-module
    mvn test
    cd ..
    
    # Test Frontend
    print_status "Testing Frontend..."
    cd frontend
    npm run test -- --watchAll=false
    cd ..
    
    print_success "All tests completed!"
}

# Main execution
main() {
    print_status "Starting development setup..."
    
    check_requirements
    setup_database
    build_ejb
    build_backend
    setup_frontend
    run_tests
    
    print_success "Development environment setup completed!"
    print_status "You can now run:"
    print_status "  - Backend: cd backend-module && mvn spring-boot:run"
    print_status "  - Frontend: cd frontend && npm start"
    print_status "  - Database: java -jar h2-*.jar"
}

# Run main function
main "$@"
