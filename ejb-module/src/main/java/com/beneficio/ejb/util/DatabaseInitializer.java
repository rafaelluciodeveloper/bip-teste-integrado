package com.beneficio.ejb.util;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton responsável pela inicialização automática do banco de dados.
 * Carrega e executa scripts SQL na ordem correta durante a inicialização da aplicação.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Singleton
@Startup
public class DatabaseInitializer {

    @PersistenceContext
    private EntityManager em;

    /**
     * Inicializa o banco de dados executando os scripts SQL necessários.
     * Verifica se a tabela já existe antes de executar os scripts para evitar
     * execuções desnecessárias em reinicializações.
     * 
     * @throws Exception se houver erro durante a inicialização
     */
    @PostConstruct
    public void initializeDatabase() {
        System.out.println("🚀 Inicializando banco de dados...");
        
        try {
            if (tableExists()) {
                System.out.println("✅ Tabela BENEFICIO já existe, pulando inicialização");
                return;
            }
            
            executeScript("db/schema.sql");
            System.out.println("✅ Schema criado com sucesso");
            
            executeScript("db/seed.sql");
            System.out.println("✅ Dados iniciais inseridos com sucesso");
            
            System.out.println("🎉 Banco de dados inicializado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifica se a tabela BENEFICIO já existe no banco de dados.
     * 
     * @return true se a tabela existe, false caso contrário
     */
    private boolean tableExists() {
        try {
            em.createNativeQuery("SELECT COUNT(*) FROM BENEFICIO").getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Executa um script SQL carregado do classpath.
     * 
     * @param scriptPath caminho do script no classpath
     * @throws Exception se houver erro ao carregar ou executar o script
     */
    private void executeScript(String scriptPath) throws Exception {
        System.out.println("📄 Executando script: " + scriptPath);
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(scriptPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            if (inputStream == null) {
                throw new RuntimeException("Script não encontrado: " + scriptPath);
            }
            
            List<String> statements = parseStatements(reader);
            
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    System.out.println("🔧 Executando: " + statement.substring(0, Math.min(50, statement.length())) + "...");
                    em.createNativeQuery(statement).executeUpdate();
                }
            }
        }
    }

    /**
     * Analisa um arquivo SQL e extrai as declarações individuais.
     * Remove comentários e divide o conteúdo em statements válidos.
     * 
     * @param reader BufferedReader para leitura do arquivo
     * @return lista de statements SQL válidos
     * @throws Exception se houver erro durante a leitura
     */
    private List<String> parseStatements(BufferedReader reader) throws Exception {
        List<String> statements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("--.*$", "").trim();
            
            if (line.isEmpty()) {
                continue;
            }
            
            currentStatement.append(line).append(" ");
            
            if (line.endsWith(";")) {
                String statement = currentStatement.toString().trim();
                if (!statement.isEmpty()) {
                    statements.add(statement);
                }
                currentStatement = new StringBuilder();
            }
        }
        
        if (currentStatement.length() > 0) {
            String statement = currentStatement.toString().trim();
            if (!statement.isEmpty()) {
                statements.add(statement);
            }
        }
        
        return statements;
    }
}
