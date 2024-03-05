package Netflix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AVL extends BST {

    public AVL() {
        super();
    }

    public AVL(BTNode root) {
        super(root);
    }

    @Override
    public void insert(ProgramaNetflix data) {
        super.setRoot(insertHelper(super.getRoot(), null, data));
    }

    private BTNode insertHelper(BTNode node, BTNode parent, ProgramaNetflix data) {
        if (node == null) {
            return new BTNode(data, parent);
        }

        if (data.getId().compareTo(node.getData().getId()) < 0) {
            node.setLeft(insertHelper(node.getLeft(), node, data));
        } else if (data.getId().compareTo(node.getData().getId()) > 0) {
            node.setRight(insertHelper(node.getRight(), node, data));
        } else {
            // Em árvores AVL, duplicatas não são permitidas.
            return node;
        }

        // Atualiza o fator de balanceamento e realiza rotações
        updateBalanceFactor(node);
        return balance(node);
    }

    @Override
    public void remove(String id) {
        super.setRoot(removeHelper(super.getRoot(), id));
    }

    private BTNode removeHelper(BTNode node, String id) {
        if (node == null) {
            return null;
        }

        if (id.compareTo(node.getData().getId()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), id));
        } else if (id.compareTo(node.getData().getId()) > 0) {
            node.setRight(removeHelper(node.getRight(), id));
        } else {
            node = removeNode(node, id);
        }

        // Atualiza o fator de balanceamento e realiza rotações
        updateBalanceFactor(node);
        return balance(node);
    }

    private BTNode balance(BTNode node) {
        if (node == null) {
            return null;
        }

        // Realiza rotações com base no fator de balanceamento
        if (node.getFb() > 1) {
            BTNode leftChild = node.getLeft();
            if (leftChild != null) {
                if (leftChild.getFb() >= 0) {
                    // Caso Esquerda-Esquerda
                    return rotateRight(node);
                } else {
                    // Caso Esquerda-Direita
                    node.setLeft(rotateLeft(leftChild));
                    return rotateRight(node);
                }
            }
        } else if (node.getFb() < -1) {
            BTNode rightChild = node.getRight();
            if (rightChild != null) {
                if (rightChild.getFb() <= 0) {
                    // Caso Direita-Direita
                    return rotateLeft(node);
                } else {
                    // Caso Direita-Esquerda
                    node.setRight(rotateRight(rightChild));
                    return rotateLeft(node);
                }
            }
        }

        return node;
    }

    // Realiza uma rotação à esquerda
    private BTNode rotateLeft(BTNode node) {
        BTNode newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        updateBalanceFactor(node);
        updateBalanceFactor(newRoot);
        return newRoot;
    }

    // Realiza uma rotação à direita
    private BTNode rotateRight(BTNode node) {
        BTNode newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        updateBalanceFactor(node);
        updateBalanceFactor(newRoot);
        return newRoot;
    }

    // Atualiza o fator de balanceamento de um nó
    private void updateBalanceFactor(BTNode node) {
        if (node != null) {
            node.updateBalanceFactor();
        }
    }

    public List<ProgramaNetflix> filterTitlesByAgeAndYear(String ageCertification, int year) {
        List<ProgramaNetflix> filteredTitles = new ArrayList<>();
        filterTitlesByAgeAndYear(getRoot(), ageCertification, year, filteredTitles);
        return filteredTitles;
    }

    private void filterTitlesByAgeAndYear(BTNode node, String ageCertification, int year,
            List<ProgramaNetflix> filteredTitles) {
        if (node != null) {
            filterTitlesByAgeAndYear(node.getLeft(), ageCertification, year, filteredTitles);

            ProgramaNetflix title = node.getData();
            if (isAgeCertificationAndBeforeYear(title, ageCertification, year)) {
                filteredTitles.add(title);
            }

            filterTitlesByAgeAndYear(node.getRight(), ageCertification, year, filteredTitles);
        }
    }

    private boolean isAgeCertificationAndBeforeYear(ProgramaNetflix title, String ageCertification, int year) {
        return title.getAgeCertification().equalsIgnoreCase(ageCertification) && title.getReleaseYear() < year;
    }

    public void titlesReleasedBeforeYear(int targetYear) {
        List<ProgramaNetflix> titlesBeforeYear = new ArrayList<>();
        findTitlesReleasedBeforeYear(getRoot(), targetYear, titlesBeforeYear);
        displayResults(titlesBeforeYear);
    }

    private void findTitlesReleasedBeforeYear(BTNode node, int targetYear, List<ProgramaNetflix> titles) {
        if (node != null) {
            findTitlesReleasedBeforeYear(node.getLeft(), targetYear, titles);

            ProgramaNetflix title = node.getData();
            if (title.getReleaseYear() < targetYear) {
                titles.add(title);
            }

            findTitlesReleasedBeforeYear(node.getRight(), targetYear, titles);
        }
    }

    public void topTitlesHighestTMDB(int n) {
        List<ProgramaNetflix> topTitles = new ArrayList<>();
        PriorityQueue<ProgramaNetflix> minHeap = new PriorityQueue<>(
                Comparator.comparingDouble(ProgramaNetflix::getTmdbScore));

        findTopTitlesHighestTMDB(getRoot(), n, minHeap);

        while (!minHeap.isEmpty()) {
            topTitles.add(minHeap.poll());
        }

        displayResults(topTitles);
    }

    private void findTopTitlesHighestTMDB(BTNode node, int n, PriorityQueue<ProgramaNetflix> minHeap) {
        if (node != null) {
            findTopTitlesHighestTMDB(node.getLeft(), n, minHeap);

            ProgramaNetflix title = node.getData();
            minHeap.offer(title);

            if (minHeap.size() > n) {
                // Mantém o tamanho do heap como N
                minHeap.poll();
            } else if (minHeap.size() == n + 1) {
                // Se o tamanho do heap for exatamente N + 1, remove o título com a maior pontuação TMDB
                minHeap.poll();
            }

            findTopTitlesHighestTMDB(node.getRight(), n, minHeap);
        }
    }

    public void topTitlesLowestTMDB(int n) {
        List<ProgramaNetflix> topTitles = new ArrayList<>();
        PriorityQueue<ProgramaNetflix> maxHeap = new PriorityQueue<>(
                Comparator.comparingDouble(ProgramaNetflix::getTmdbScore).reversed());

        findTopTitlesLowestTMDB(getRoot(), n, maxHeap);

        while (!maxHeap.isEmpty()) {
            topTitles.add(maxHeap.poll());
        }

        displayResults(topTitles);
    }

    private void findTopTitlesLowestTMDB(BTNode node, int n, PriorityQueue<ProgramaNetflix> maxHeap) {
        if (node != null) {
            findTopTitlesLowestTMDB(node.getLeft(), n, maxHeap);

            ProgramaNetflix title = node.getData();
            maxHeap.offer(title);

            if (maxHeap.size() > n) {
                // Mantém o tamanho do heap como N
                maxHeap.poll();
            }

            findTopTitlesLowestTMDB(node.getRight(), n, maxHeap);
        }
    }

    public void getShows() {
        List<ProgramaNetflix> showList = getAllShows();
        displayResults(showList);
    }

    private List<ProgramaNetflix> getAllShows() {
        List<ProgramaNetflix> showList = new ArrayList<>();
        PriorityQueue<ProgramaNetflix> heap = new PriorityQueue<>(Comparator.comparing(ProgramaNetflix::getTitulo));

        getAllShows(getRoot(), heap);

        while (!heap.isEmpty()) {
            showList.add(heap.poll());
        }

        return showList;
    }

    private void getAllShows(BTNode node, PriorityQueue<ProgramaNetflix> heap) {
        if (node != null) {
            getAllShows(node.getLeft(), heap);

            ProgramaNetflix title = node.getData();
            if (title.getTipoDePrograma().equalsIgnoreCase("SHOW")) {
                heap.offer(title);
            }

            getAllShows(node.getRight(), heap);
        }
    }

    private void displayResults(List<ProgramaNetflix> titles) {
        if (titles.isEmpty()) {
            System.out.println("Nenhum título encontrado.");
        } else {
            System.out.println("Top Títulos:");
            for (ProgramaNetflix title : titles) {
                System.out.println(title);
            }
        }
    }
}
