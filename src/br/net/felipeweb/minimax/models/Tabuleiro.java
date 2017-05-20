package br.net.felipeweb.minimax.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipeweb on 20/05/17.
 */
public class Tabuleiro {

    private String[][] mapa = new String[3][3];
    private Jogador jogador;
    private int valor = 0;

    private Tabuleiro pai;
    private List<Tabuleiro> filhos = new ArrayList<>();

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public String getXY(int linha, int coluna) {
        return mapa[linha][coluna];
    }

    public String[][] getMapa() {
        return mapa;
    }

    public void setX(int linha, int coluna) {
        mapa[linha][coluna] = "X";
    }

    public void setO(int linha, int coluna) {
        mapa[linha][coluna] = "O";
    }

    private boolean verificaHorizontal(int linha) {
        return ("O".equals(mapa[linha][0]) && "O".equals(mapa[linha][1]) && "O".equals(mapa[linha][2]));
    }

    private boolean verificaVertical(int coluna) {
        return ("O".equals(mapa[0][coluna]) && "O".equals(mapa[1][coluna]) && "O".equals(mapa[2][coluna]));
    }

    private boolean verificaDiagonalPrincipal() {
        return ("O".equals(mapa[0][0]) && "O".equals(mapa[1][1]) && "O".equals(mapa[2][2]));
    }

    private boolean verificaDiagonalSecundaria() {
        return ("O".equals(mapa[0][2]) && "O".equals(mapa[1][1]) && "O".equals(mapa[2][0]));
    }

    private boolean verificaPerdeuHorizontal(int linha) {
        return ("X".equals(mapa[linha][0]) && "X".equals(mapa[linha][1]) && "X".equals(mapa[linha][2]));
    }

    private boolean verificaPerdeuVertical(int coluna) {
        return ("X".equals(mapa[0][coluna]) && "X".equals(mapa[1][coluna]) && "X".equals(mapa[2][coluna]));
    }

    private boolean verificaPerdeuDiagonalPrincipal() {
        return ("X".equals(mapa[0][0]) && "X".equals(mapa[1][1]) && "X".equals(mapa[2][2]));
    }

    private boolean verificaPerdeuDiagonalSecundaria() {
        return ("X".equals(mapa[0][2]) && "X".equals(mapa[1][1]) && "X".equals(mapa[2][0]));
    }

    /**
     * Verifica se houve empate
     * @return
     */
    public boolean empate() {
        System.out.println("verifica empate");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mapa[i][j] == null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Verifica se o Computador Perdeu
     * @return
     */
    public boolean perdeu() {
        System.out.println("verifica perdeu");
        return (verificaPerdeuHorizontal(0)
                || verificaPerdeuHorizontal(1)
                || verificaPerdeuHorizontal(2)
                || verificaPerdeuVertical(0)
                || verificaPerdeuVertical(1)
                || verificaPerdeuVertical(2)
                || verificaPerdeuDiagonalPrincipal()
                || verificaPerdeuDiagonalSecundaria());
    }

    /**
     * Verifica se o computador ganhou
     * @return
     */
    public boolean ganhou() {
        System.out.println("verifica ganhou");
        return (verificaHorizontal(0)
                || verificaHorizontal(1)
                || verificaHorizontal(2)
                || verificaVertical(0)
                || verificaVertical(1)
                || verificaVertical(2)
                || verificaDiagonalPrincipal()
                || verificaDiagonalSecundaria());
    }

    /**
     * Verifica se ele é um nó folha
     * @return TRUE caso for folha
     * FALSE caso não for folha
     */
    public boolean isTerminal() {
        return ganhou() || perdeu() || empate();
    }

    /**
     *
     * @return (int) o resultado de acordo com o tabuleiro, se o computador Ganhou(1), Perdeu(-1) ou Empatou(0).
     */
    public int getResultado() {
        if (ganhou()) {
            valor=1;
            return 1;
        } else {
            if (perdeu()) {
                valor=-1;
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Mostra o tabuleiro formatado no Console.
     */
    public void mostra() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(mapa[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("Utilidade: "+this.getValor());
    }

    /**
     * Só vai pegar a lista dos filhos
     * Para descobrir quem são os filhos usar o getFilhos()
     * @see getFilhos(Tabuleiro t)
     *
     * @return
     */
    public List<Tabuleiro> getTodosFilhos(){
        return filhos;
    }

    /**
     * Vai buscar todas as possibilidades (Filhos) do nó
     * @param t
     * @return
     */
    public List<Tabuleiro> getFilhos(Tabuleiro t) {
        filhos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    Tabuleiro filho = t.clone();
                    if (filho.getXY(i, j) == null) {
                        if (jogador.equals(Jogador.Max)) {
                            filho.setO(i, j);
                        } else {
                            filho.setX(i, j);
                        }
                        filho.getResultado();
                        filhos.add(filho);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return filhos;
    }

    @Override
    protected Tabuleiro clone() throws CloneNotSupportedException {
        Tabuleiro t = new Tabuleiro();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(mapa[i], 0, t.getMapa()[i], 0, 3);
        }
        return t;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s+=mapa[i][j]+",";
            }
        }
        return s+"]\n";
    }

}
