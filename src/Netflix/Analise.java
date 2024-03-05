// 
// Feito por Luis Castanho e Julian Teixeira em 20/11/2023
// 
// Lê arquivo csv que contém títulos da Netflix e suas respectivas informações
// e armazena as informações em duas árvores (BST e AVL). Para armazenar todas as 
// informações nos nós, criamos a classe ProgramaNetflix. 
// A partir dessas estruturas, o usuário pode interagir com diversas opções de busca, inserção, remoção, etc.
// Como parte do projeto, o usuário pode acessar algumas análises dos dados pré-determinadas.
//
//
//
// Made by Luis Castanho and Julian Teixeira on 11/20/2023
// 
// Reads a csv file containing Netflix titles and their respective information
// and stores the information in two trees (BST and AVL). To store all the 
// information in the nodes, we created the ProgramaNetflix class. 
// Based on these structures, the user can interact with various options such as search, insertion, removal, etc.
// As part of the project, the user can access some predetermined data analysis.

//
package Netflix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Analise {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice;
		// Criar árvores BST e AVL
		BST bstTree = new BST();
		AVL avlTree = new AVL();

		do {
			System.out.println("\nMenu:");
			System.out.println("1. Leia o arquivo e crie as árvores AVL e BST");
			System.out.println("2. Analises");
			System.out.println("3. Inserir novo programa");
			System.out.println("4. Buscar programa");
			System.out.println("5. Remover programa");
			System.out.println("6. Consultar altura das árvores AVL e BST");
			System.out.println("7. Salvar dados da árvore em arquivo");
			System.out.println("0. Exit");

			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				// Lê os arquivos e cria as árvores
				readData(scanner, avlTree, bstTree);
				System.out.println("\nArvores criadas com sucesso");
				System.out.println(avlTree.levelOrderTraversal());
				break;
			case 2:
				System.out.println("\nAnalises disponíveis na AVL:");
				System.out.println("1. Top 10 titulos com age_certification = TV-14 e genero 'crime'");
				System.out.println("2. Titulos lançados antes de um determinado ano");
				System.out.println("3. Top N titulos com as menores pontuacoes do TMDB: ");
				System.out.println("4. Titulos que sao apenas shows");
				System.out.println("5. Top N titulos com as maiores pontuacoes do TMDB: ");

				System.out.print("Escolha uma analise (1-5): ");
				int analysisChoice = scanner.nextInt();
				scanner.nextLine();

				switch (analysisChoice) {
				case 1:
					System.out.print("Informe o ano limite: ");
					int targetYear = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Informe a classificação etária desejada: ");
					String targetAge = scanner.nextLine();
					List<ProgramaNetflix> filteredTitles = avlTree.filterTitlesByAgeAndYear(targetAge, targetYear);
					for (ProgramaNetflix title : filteredTitles) {
						System.out.println();
						System.out.print(title);
						System.out.print("-----------------------------\n");
					}
					break;
				case 2:
					System.out.print("Informe o ano limite: ");
					int limitYear = scanner.nextInt();
					avlTree.titlesReleasedBeforeYear(limitYear);
					break;
				case 3:
					System.out.print("Informe o valor de N para os menores TMDB scores: ");
					int nLowestTMDB = scanner.nextInt();
					avlTree.topTitlesLowestTMDB(nLowestTMDB);
					break;
				case 4:
					avlTree.getShows();
					break;
				case 5:
					System.out.print("Informe o valor de N para os maiores TMDB scores: ");
					int nHighestTMDB = scanner.nextInt();
					avlTree.topTitlesHighestTMDB(nHighestTMDB);
					break;
				default:
					System.out.println("Escolha inválida. Por favor, escolha uma opção válida (1-5).");
					break;
				}
				break;
			case 3:
				// Insere novo programa
				boolean flagInsert = false;

				while (!flagInsert) {
					flagInsert = insertProgramaNetflix(scanner, bstTree, avlTree);
					if (flagInsert) {
						System.out.println("Programa inserido");
					} else {
						System.out.println("Id já existente, tente novamente.");
					}
					break;
				}
			case 4:
				// Busca um programa
				System.out.print("Informe o Id do programa a ser procurado: ");
				String searchId = scanner.next();
				estimateSearchTime(avlTree, bstTree, searchId);
				break;
			case 5:
				boolean flagRemove = removeProgramaNetflix(scanner, bstTree, avlTree);
				if (flagRemove) {
					System.out.println("Programa removido");
				} else {
					System.out.println("Não foi possivel encontrar o programa");
				}
				break;
			case 6:
				displayTreeHeight(bstTree, avlTree);
				break;

			case 7:
				saveDataToFile(scanner, avlTree);
				break;
			case 0:
				System.out.println("Exiting program. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		} while (choice != 0);

	}

	private static ProgramaNetflix createProgramaNetflix(String[] attributes) {
		String id = attributes[0];
		String titulo = attributes[1];
		String showType = attributes[2];
		String descricao = attributes[3];

		float releaseYear = 0;
		if (!attributes[4].isEmpty()) {
			releaseYear = Float.parseFloat(attributes[4]);
		}

		String ageCertification = attributes[5];

		float runtime = 0;
		if (!attributes[6].isEmpty()) {
			runtime = Float.parseFloat(attributes[6]);
		}

		String[] generos = attributes[7].isEmpty() ? new String[0] : attributes[7].split(",");
		String[] productionCountries = attributes[8].isEmpty() ? new String[0] : attributes[8].split(",");

		float temporadas = 0;
		if (!attributes[9].isEmpty()) {
			temporadas = Float.parseFloat(attributes[9]);
		}

		String imdbId = attributes[10];

		float imdbScore = 0.0f;
		if (!attributes[11].isEmpty()) {
			imdbScore = Float.parseFloat(attributes[11]);
		}

		float imdbVotes = 0.0f;
		if (!attributes[12].isEmpty()) {
			imdbVotes = Float.parseFloat(attributes[12]);
		}

		float tmdbPopularity = 0.0f;
		if (!attributes[13].isEmpty()) {
			tmdbPopularity = Float.parseFloat(attributes[13]);
		}

		float tmdbScore = 0.0f;
		if (!attributes[14].isEmpty()) {
			tmdbScore = Float.parseFloat(attributes[14]);
		}

		return new ProgramaNetflix(id, titulo, showType, descricao, releaseYear, ageCertification, runtime, generos,
				productionCountries, temporadas, imdbId, imdbScore, imdbVotes, tmdbPopularity, tmdbScore);
	}

	private static ProgramaNetflix createProgramaNetflixFromUserInput(Scanner scanner) {

		System.out.println("Digite os dados do novo programa Netflix:");

		System.out.print("ID: ");
		String id = scanner.nextLine();

		System.out.print("Título: ");
		String titulo = scanner.nextLine();

		System.out.print("Tipo de Programa (SHOW ou MOVIE): ");
		String tipoDePrograma = scanner.nextLine();

		System.out.print("Descrição: ");
		String descricao = scanner.nextLine();

		System.out.print("Ano de Lançamento: ");
		float releaseYear = scanner.nextFloat();
		scanner.nextLine();

		System.out.print("Classificação Etária: ");
		String ageCertification = scanner.nextLine();

		System.out.print("Duração (em minutos): ");
		float runtime = scanner.nextFloat();
		scanner.nextLine();

		System.out.print("Gêneros (separados por vírgula): ");
		String[] generos = scanner.next().split(",");

		System.out.print("Países de Produção (separados por vírgula): ");
		String[] productionCountries = scanner.next().split(",");

		System.out.print("Número de Temporadas (para SHOW) ou 0 (para MOVIE): ");
		float temporadas = scanner.nextFloat();
		scanner.nextLine();

		System.out.print("ID do IMDb: ");
		String imdbId = scanner.nextLine();

		System.out.print("Pontuação do IMDb: ");
		float imdbScore = scanner.nextFloat();
		scanner.nextLine();
		System.out.print("Votos do IMDb: ");
		float imdbVotes = scanner.nextFloat();
		scanner.nextLine();
		System.out.print("Popularidade do TMDb: ");
		float tmdbPopularity = scanner.nextFloat();
		scanner.nextLine();
		System.out.print("Pontuação do TMDb: ");
		float tmdbScore = scanner.nextFloat();
		scanner.nextLine();
		return new ProgramaNetflix(id, titulo, tipoDePrograma, descricao, releaseYear, ageCertification, runtime,
				generos, productionCountries, temporadas, imdbId, imdbScore, imdbVotes, tmdbPopularity, tmdbScore);
	}

	private static String[] splitCSVLine(String line) {
		// Lógica para dividir a linha tratando vírgulas dentro das aspas
		// Pode ser necessário ajustar dependendo de casos específicos
		String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].replace("\"", "").trim();
		}

		return parts;
	}

	private static void readData(Scanner scanner, AVL avlTree, BST bstTree) {
		System.out.print("Digite o nome do arquivo: ");
		String fileName = scanner.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Dividir a linha usando a vírgula como delimitador

				String[] attributes = splitCSVLine(line);

				// Verificar se todos os 15 atributos estão presentes
				if (attributes.length == 15) {
					// Criar um objeto ProgramaNetflix apenas se todos os atributos estiverem
					// preenchidos
					ProgramaNetflix programa = createProgramaNetflix(attributes);

					// Inserir na árvore BST
					bstTree.insert(programa);

					// Inserir na árvore AVL
					avlTree.insert(programa);

				} else {
					// Atributos incompletos, descartar e não inserir nas árvores
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean insertProgramaNetflix(Scanner scanner, BST bstTree, AVL avlTree) {
		ProgramaNetflix newProgram = createProgramaNetflixFromUserInput(scanner);
		// Para inserir na BST
		if (bstTree.search_id(newProgram.getId()) == null && avlTree.search_id(newProgram.getId()) == null) {
			bstTree.insert(newProgram);
			avlTree.insert(newProgram);
			return true;
		} else {

			return false;
		}
	}

	private static void estimateSearchTime(AVL avlTree, BST bstTree, String searchId) {
		System.out.println("AVL");
		long startTimeAVL = System.nanoTime();
		avlTree.search_countComparisons(searchId);
		long endTimeAVL = System.nanoTime();
		long durationAVL = TimeUnit.NANOSECONDS.toMicros(endTimeAVL - startTimeAVL);
		System.out.println("Tempo de execução: " + durationAVL + " ms");

		System.out.println("BST");
		long startTimeBST = System.nanoTime();
		bstTree.search_countComparisons(searchId);
		long endTimeBST = System.nanoTime();
		long durationBST = TimeUnit.NANOSECONDS.toMicros(endTimeBST - startTimeBST);
		System.out.println("Tempo de execução: " + durationBST + " ms");
		System.out.println("--------------------------------------");
		BTNode no = avlTree.search_id(searchId);
		if (no != null) {
			System.out.print("Nó encontrado: \n");
			System.out.print(no.getData());
			System.out.println("--------------------------------------");
		} else {
			System.out.println("\nNo não encontrado");
		}

	}

	private static boolean removeProgramaNetflix(Scanner scanner, BST bstTree, AVL avlTree) {
		System.out.print("Informe o Id do programa a ser removido: ");
		String id = scanner.nextLine();
		if (bstTree.search_id(id) != null && avlTree.search_id(id) != null) {
			bstTree.remove(id);
			avlTree.remove(id);
			return true;
		} else {
			return false;
		}

	}

	private static void displayTreeHeight(BST bstTree, AVL avlTree) {
		System.out.println("Altura da árvore BST: " + bstTree.getHeight());
		System.out.println("Altura da árvore AVL: " + avlTree.getHeight());
	}

	private static void saveDataToFile(Scanner scanner, AVL avlTree) {
		System.out.print("Enter the file name for saving data: ");
		String fileName = scanner.next();

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			saveNodeDataToFile(avlTree.getRoot(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Data saved to the file successfully!");
	}
	
	private static void saveNodeDataToFile(BTNode node, PrintWriter writer) {
		if (node != null) {
			writer.println(node.getData());

			saveNodeDataToFile(node.getLeft(), writer);
			saveNodeDataToFile(node.getRight(), writer);
		}
	}

}
