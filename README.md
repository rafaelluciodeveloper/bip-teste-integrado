# Teste Integrado

Sistema de gerenciamento de benefícios com arquitetura modular.

## 🏗️ Arquitetura

### Backend (Spring Boot)
- **Tecnologia**: Java 17, Spring Boot, Spring Data JPA
- **Porta**: 8081
- **API**: RESTful com documentação Swagger

### EJB Module
- **Tecnologia**: Jakarta EE, EJB, JPA
- **Função**: Lógica de negócio e persistência

### Frontend (Angular)
- **Tecnologia**: Angular, TypeScript, Bootstrap
- **Porta**: 8082
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

## 🌐 Acessos

### Frontend
- **URL**: http://localhost:8082
- **Funcionalidades**: CRUD de benefícios, transferências, histórico

### Swagger (API Documentation)
- **URL**: http://localhost:8081/beneficio-api/swagger-ui/index.html
- **Endpoints**: Documentação completa da API REST

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
├── ear-module/         # EAR: empacota o EJB (negócio/JPA) e o WAR (API REST)
├── ejb-module/         # Jakarta EE EJB
├── frontend/           # Angular Application
└── docker-compose.yml  # Docker configuration
```
