/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dominio.EntidadeDominio;
import Dominio.Peca;

/**
 *
 * @author william
 */
//checa para que qtde de estoque não seja negativo
public class StValQtdePeca implements IStrategy {
    
    @Override
    public String processar(EntidadeDominio entidade) {
        
        Peca peca = (Peca) entidade;
        String msg="";
        if(peca.getEstoque().getQtdeEstoque()<0)
            msg = "Qtde inválida. ";
        return msg;
    }
    
}
