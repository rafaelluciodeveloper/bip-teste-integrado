package com.beneficio.ejb.constants;

/**
 * Constantes de mensagens do sistema.
 * Centraliza todas as mensagens de erro, sucesso e validação do EJB.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
public final class Messages {

    // Mensagens de Erro - Validações
    public static final String PARAMETROS_NAO_PODEM_SER_NULOS = "Parâmetros não podem ser nulos";
    public static final String BENEFICIO_ORIGEM_DESTINO_IGUAIS = "Benefício origem e destino não podem ser iguais";
    public static final String VALOR_DEVE_SER_POSITIVO = "Valor deve ser positivo";
    public static final String NOME_OBRIGATORIO = "Nome do benefício é obrigatório";
    public static final String VALOR_OBRIGATORIO = "Valor é obrigatório";
    public static final String NOME_MAXIMO_CARACTERES = "Nome do benefício não pode ter mais de 100 caracteres";
    public static final String DESCRICAO_MAXIMO_CARACTERES = "Descrição não pode ter mais de 255 caracteres";
    public static final String VALOR_NAO_PODE_SER_NEGATIVO = "Valor não pode ser negativo";
    public static final String VALOR_EXCEDE_LIMITE = "Valor excede o limite máximo permitido";

    // Mensagens de Erro - Negócio
    public static final String BENEFICIO_NAO_ENCONTRADO = "Benefício não encontrado com ID: %d";
    public static final String BENEFICIO_ORIGEM_NAO_ENCONTRADO = "Benefício origem não encontrado: %d";
    public static final String BENEFICIO_DESTINO_NAO_ENCONTRADO = "Benefício destino não encontrado: %d";
    public static final String BENEFICIO_ORIGEM_INATIVO = "Benefício origem está inativo";
    public static final String BENEFICIO_DESTINO_INATIVO = "Benefício destino está inativo";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente. Saldo atual: %s, Valor solicitado: %s";
    public static final String NAO_PODE_EXCLUIR_COM_TRANSFERENCIAS = "Não é possível excluir benefício com transferências associadas";

    // Construtor privado para evitar instanciação
    private Messages() {
        throw new UnsupportedOperationException("Classe de constantes não deve ser instanciada");
    }
}
