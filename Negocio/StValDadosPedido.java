/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dominio.EntidadeDominio;
import Dominio.Pedido;

/**
 *
 * @author william
 */
//validar dados obrigatórios de pedido
public class StValDadosPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        StringBuilder msg = new StringBuilder();
        if (pedido.getSetor().getNmSetor()== null || pedido.getSetor().getNmSetor().trim().equals("")) {
            msg.append("Setor inválido. ");
        }
        if (pedido.getFuncionario().getNome()== null || pedido.getFuncionario().getNome().trim().equals("")) {
            msg.append("Nome de Funcionário inválido. ");
        }
        return msg.toString();
    }
    
}
