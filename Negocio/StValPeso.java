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
//valida dados de peça para que não aceite peso negativo ou igual a zero
public class StValPeso implements IStrategy{

    
    @Override
    public String processar(EntidadeDominio entidade) {
        Peca peca = (Peca)entidade;
        String msg="";
        if(peca.getPeso()<=0 )
        {
           msg = "Peso inválido. ";
        }
        return msg;
    }
    
}
