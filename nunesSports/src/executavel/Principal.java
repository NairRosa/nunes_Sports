package executavel;

import modelos.Produto;
import banco.ProdutoDAO;
import java.math.BigDecimal;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println();
                System.out.println("***** MENU PRINCIPAL *****");
                System.out.println();
                System.out.println("[1] Cadastrar novo produto.");
                System.out.println("[2] Editar produto existente.");
                System.out.println("[3] Excluir produto.");
                System.out.println("[4] Ver relação de produtos.");
                System.out.println();
                System.out.println("[0] Encerrar programa.");
                System.out.println();
                System.out.print("Digite a opção desejada: ");

                int op;
                try {
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    sc.nextLine(); // Limpar o buffer
                    continue;
                }
                sc.nextLine(); // Limpar o buffer do Scanner
                System.out.println();

                switch (op) {
                    case 1: // Cadastrar novo produto
                        System.out.print("Digite o nome do produto: ");
                        String nome = sc.nextLine();
                        System.out.print("Digite o código do produto: ");
                        String codigo = sc.nextLine();
                        System.out.print("Digite a descrição do produto: ");
                        String descricao = sc.nextLine();
                        System.out.print("Digite o preço do produto: ");
                        BigDecimal preco;
                        try {
                            preco = sc.nextBigDecimal();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida. Digite um número válido para o preço.");
                            sc.nextLine(); // Limpar o buffer
                            continue;
                        }
                        sc.nextLine(); // Limpar o buffer
                        Produto novoProduto = new Produto(nome, codigo, descricao, preco);
                        produtoDAO.adicionarProduto(novoProduto);
                        System.out.println("Produto cadastrado com sucesso!");
                        break;

                    case 2: // Editar produto existente
                        System.out.print("Digite o código do produto a ser editado: ");
                        String codigoEditar = sc.nextLine();
                        System.out.print("Digite o novo nome do produto: ");
                        String novoNome = sc.nextLine();
                        System.out.print("Digite a nova descrição do produto: ");
                        String novaDescricao = sc.nextLine();
                        System.out.print("Digite o novo preço do produto: ");
                        BigDecimal novoPreco;
                        try {
                            novoPreco = sc.nextBigDecimal();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida. Digite um número válido para o preço.");
                            sc.nextLine(); // Limpar o buffer
                            continue;
                        }
                        sc.nextLine(); // Limpar o buffer
                        Produto produtoEditado = new Produto(novoNome, codigoEditar, novaDescricao, novoPreco);
                        produtoDAO.editarProduto(codigoEditar, produtoEditado);
                        System.out.println("Produto editado com sucesso!");
                        break;

                    case 3: // Excluir produto
                        System.out.print("Digite o código do produto a ser excluído: ");
                        String codigoExcluir = sc.nextLine();
                        produtoDAO.excluirProduto(codigoExcluir);
                        System.out.println("Produto excluído com sucesso!");
                        break;

                    case 4: // Ver relação de produtos
                        try {
                            List<Produto> produtos = produtoDAO.listarProdutos();
                            if (produtos.isEmpty()) {
                                System.out.println("Nenhum produto cadastrado.");
                            } else {
                                produtos.forEach(System.out::println);
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao listar produtos: " + e.getMessage());
                            // Você pode adicionar mais lógica aqui, se necessário
                        }
                        break;

                    case 0: // Encerrar programa
                        System.out.println("Encerrando o programa...");
                        return;

                    default: // Caso escolham uma opção inexistente
                        System.out.println("Opção inválida, tente novamente.");
                        break;
                }
            }
        }
    }
}