# Teste Integrado

Sistema de gerenciamento de benefícios com arquitetura modular.

## 🏗️ Arquitetura

### Backend (Spring Boot)
- **Tecnologia**: Java 17, Spring Boot, Spring Data JPA
- **Porta**: 8080
- **API**: RESTful com documentação Swagger

### EJB Module
- **Tecnologia**: Jakarta EE, EJB, JPA
- **Função**: Lógica de negócio e persistência

### Frontend (Angular)
- **Tecnologia**: Angular, TypeScript, Bootstrap
- **Porta**: 4200
- **Interface**: Web responsiva

### Banco de Dados
- **Tecnologia**: H2 Database (in-memory)
- **Configuração**: Automática via JPA

## 🚀 Como Executar

### Docker Compose (Recomendado)
```bash
# Iniciar todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar serviços
docker-compose down
```

### Desenvolvimento Local
```bash
# Backend
cd backend-module
mvn spring-boot:run

# Frontend
cd frontend
npm start
```

## 🌐 Acessos

### Frontend
- **URL**: http://localhost:4200
- **Funcionalidades**: CRUD de benefícios, transferências, histórico

### Swagger (API Documentation)
- **URL**: http://localhost:8080/swagger-ui.html
- **Endpoints**: Documentação completa da API REST

### Health Check
- **Backend**: http://localhost:8080/actuator/health
- **Frontend**: http://localhost:4200/health

## 📋 Funcionalidades

- ✅ **CRUD de Benefícios** - Criar, listar, editar, excluir
- ✅ **Transferências** - Transferir valores entre benefícios
- ✅ **Histórico** - Visualizar histórico de transferências
- ✅ **Validações** - Regras de negócio e integridade
- ✅ **API REST** - Endpoints documentados

## 🛠️ Tecnologias

| Módulo | Tecnologias |
|--------|-------------|
| **Backend** | Java 17, Spring Boot, Spring Data JPA, Maven |
| **EJB** | Jakarta EE, EJB, JPA, Hibernate |
| **Frontend** | Angular, TypeScript, Bootstrap, npm |
| **Database** | H2 (in-memory) |
| **Container** | Docker, Docker Compose |
| **CI/CD** | GitHub Actions |

## 📁 Estrutura

```
├── backend-module/     # Spring Boot REST API
├── ejb-module/         # Jakarta EE EJB
├── frontend/           # Angular Application
├── db/                 # Database scripts
├── docs/               # Documentation
└── docker-compose.yml  # Docker configuration
```
