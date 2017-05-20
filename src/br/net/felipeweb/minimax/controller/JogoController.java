package br.net.felipeweb.minimax.controller;

import br.net.felipeweb.minimax.models.Jogador;
import br.net.felipeweb.minimax.models.Minimax;
import br.net.felipeweb.minimax.models.Tabuleiro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipeweb on 20/05/17.
 */
public class JogoController implements Initializable {

    @FXML
    Button L1C1, L1C2, L1C3, L2C1, L2C2, L2C3, L3C1, L3C2, L3C3;
    @FXML
    Label estatistica;

    private int ganhou = 0, pedeu = 0, empate = 0;
    private Tabuleiro tabuleiro = new Tabuleiro();
    private Minimax minimax = new Minimax();
    private int iniciou = 1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @FXML
    public void init() {
        System.out.println("init");
        tabuleiro = new Tabuleiro();
        setTabuleiro(tabuleiro);
        iniciou = 1 - iniciou;
        if (iniciou == 0) {
            System.out.println("Você começa!");
            tabuleiro.setJogador(Jogador.Min); // Minha vez de Jogar
        } else {
            System.out.println("Computador começa!");
            //Calculo o Minimax
            tabuleiro.setJogador(Jogador.Max);
            tabuleiro = minimax.minimaxDecision(tabuleiro);
            //Computador Joga
            setTabuleiro(tabuleiro);
        }
        estatistica.setText("Ganhou: " + ganhou + " Perdeu: " + pedeu + " Empatou: " + empate);

    }

    @FXML
    public void marcaX(ActionEvent me) {
        if (tabuleiro.getJogador().equals(Jogador.Min)) {
            //Eu Joguei
            Button celula = (Button) me.getSource();
            if (celula.getText() == null || "".equals(celula.getText())) {
                celula.setText("X");
                tabuleiro.setX(Integer.parseInt(celula.getId().charAt(1) + "") - 1, Integer.parseInt(celula.getId().charAt(3) + "") - 1);

                if (!tabuleiro.isTerminal()) {
                    System.out.println("Joga Computador");
                    //Calculo o Minimax
                    tabuleiro.setJogador(Jogador.Max);
                    tabuleiro = minimax.minimaxDecision(tabuleiro);

                    //Computador Joga
                    setTabuleiro(tabuleiro);
                    tabuleiro.setJogador(Jogador.Min);
                }
                System.out.println("Terminou:" + tabuleiro.isTerminal());

                //Mostra Resultado caso acabou o Jogo
                if (tabuleiro.isTerminal()) {
                    if (tabuleiro.ganhou()) {
                        System.out.println("ganhou!!");
                        pedeu++;
                    }
                    if (tabuleiro.perdeu()) {
                        System.out.println("perdeu");
                        ganhou++;
                    }
                    if (tabuleiro.empate()) {
                        System.out.println("empatou");
                        empate++;
                    }

                    this.init();
                }

            }
        }
    }

    private void setTabuleiro(Tabuleiro tab) {
        L1C1.setText(tab.getXY(0, 0));
        L1C2.setText(tab.getXY(0, 1));
        L1C3.setText(tab.getXY(0, 2));

        L2C1.setText(tab.getXY(1, 0));
        L2C2.setText(tab.getXY(1, 1));
        L2C3.setText(tab.getXY(1, 2));

        L3C1.setText(tab.getXY(2, 0));
        L3C2.setText(tab.getXY(2, 1));
        L3C3.setText(tab.getXY(2, 2));
    }

}
