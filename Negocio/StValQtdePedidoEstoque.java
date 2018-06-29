/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.DAOPeca;
import DAO.IDAO;
import Dominio.EntidadeDominio;
import Dominio.Peca;
import Dominio.Pedido;
import java.util.List;

/**
 *
 * @author william
 */
//checa para que número pedido de peças no pedido não ultrapasse a qtde q tem no estoque
public class StValQtdePedidoEstoque implements IStrategy {

    // public String processar(EntidadeDominio entidade) {
    @Override
    public String processar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        pedido.getPeca().getId();
        Peca peca = new Peca();
        peca.setId(pedido.getPeca().getId());
        peca.setNmPeca(pedido.getPeca().getNmPeca());
        StValExistenciaPeca vExistePeca = new StValExistenciaPeca();
        String msg;

        if (vExistePeca.processar(entidade).isEmpty()) {

            IDAO dao = new DAOPeca();
            List<EntidadeDominio> entidades = dao.consultar(peca);
        
            Peca pecas = (Peca) entidades.get(0);

            int qtdePecasEstoque = pecas.getEstoque().getQtdeEstoque();

            if (pedido.getQtde() > qtdePecasEstoque) {
                msg= "Qtde de pecas no pedido ultrapassa a qtde no estoque. ";
            } else if (pedido.getQtde()<=0){
                msg= "Qtde de pecas invalido. ";
            }else{
                msg= "";
            }
        } else {
            msg= "";
        }
        return msg;

    }

}
