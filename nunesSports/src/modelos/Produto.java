package modelos;

import java.math.BigDecimal;

public class Produto {
    private String nome;
    private String codigo;
    private String descricao;
    private BigDecimal preco;

    // Construtor padrão
    public Produto() {}

    // Construtor com todos os parâmetros
    public Produto(String nome, String codigo, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("Código: %s, Nome: %s, Descrição: %s, Preço: %.2f",
                codigo, nome, descricao, preco);
    }
}
