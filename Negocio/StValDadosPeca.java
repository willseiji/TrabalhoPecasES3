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
//validar dados obrigatórios da peça
public class StValDadosPeca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidade) {
        Peca peca = (Peca) entidade;
        StringBuilder msg = new StringBuilder();
        
        if (peca.getNmPeca()== null || peca.getNmPeca().trim().equals("")) {
            msg.append("Nome da peça inválido. ");
        }
        if(peca.getTamanho()== null || peca.getTamanho().trim().equals("")) {
            msg.append("Tamanho da peça inválido. ");
        }
        if(peca.getMaterial()== null || peca.getMaterial().trim().equals("")) {
            msg.append("Material da peça inválido. ");
        }

        return msg.toString();    
        
    }
    
}
