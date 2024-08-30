package banco;

import modelos.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/nunessports?useSSL=false&serverTimezone=UTC";
    private static final String USER = "admin1";
    private static final String PASSWORD = "admin1";

    public static void main(String[] args) {
        // SQL para criar a tabela
        String createTableSQL = "CREATE TABLE produtos (" +
                                 "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                 "nome VARCHAR(255) NOT NULL, " +
                                 "codigo VARCHAR(100) UNIQUE NOT NULL, " +
                                 "descricao TEXT, " +
                                 "preco DECIMAL(10, 2) NOT NULL" +
                                 ");";

        // Tenta estabelecer a conexão e criar a tabela
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Executa o comando SQL
            statement.execute(createTableSQL);
            System.out.println("Tabela criada com sucesso!");

        } catch (SQLException e) {
            // Exibe uma mensagem de erro mais detalhada
            System.err.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, codigo, descricao, preco) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getCodigo());
            statement.setString(3, produto.getDescricao());
            statement.setBigDecimal(4, produto.getPreco());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarProduto(String codigo, Produto novoProduto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE codigo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, novoProduto.getNome());
            statement.setString(2, novoProduto.getDescricao());
            statement.setBigDecimal(3, novoProduto.getPreco());
            statement.setString(4, codigo);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirProduto(String codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, codigo);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Produto produto = new Produto(
                    resultSet.getString("nome"),
                    resultSet.getString("codigo"),
                    resultSet.getString("descricao"),
                    resultSet.getBigDecimal("preco")
                );
                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Optional<Produto> encontrarProdutoPorCodigo(String codigo) {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";
        Optional<Produto> produtoOptional = Optional.empty();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Produto produto = new Produto(
                        resultSet.getString("nome"),
                        resultSet.getString("codigo"),
                        resultSet.getString("descricao"),
                        resultSet.getBigDecimal("preco")
                    );
                    produtoOptional = Optional.of(produto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtoOptional;
    }
}