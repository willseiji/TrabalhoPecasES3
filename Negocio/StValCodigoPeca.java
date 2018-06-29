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
//validar código da peça com a formatação de 12 caracteres obrigatórios
public class StValCodigoPeca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidade) {
        Peca peca = (Peca)entidade;
        String msg = "";
        if(peca.getId().length()!=12)
        {
            msg = "Código precisa ter 12 caracteres. ";
        }
        return msg;
    }

    
}
