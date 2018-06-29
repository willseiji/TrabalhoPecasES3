/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author william
 */
public class Pedido extends EntidadeDominio{
    private Setor setor;
    private Peca peca;
    private Funcionario funcionario;
    private int qtde;

    public Pedido() {
        
    }
    
    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }
    
    public Peca getPeca() {
        return peca;
    }
    
    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Setor getSetor() {
        return setor;
    }
    
    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    
    
    public Pedido(Peca peca,int qtde, Setor setor, Funcionario funcionario) {
        this.setor = setor;
        this.peca = peca;   
        this.qtde = qtde;
        this.funcionario=funcionario;
    }

}
