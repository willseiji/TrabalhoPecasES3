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

/**
 *
 * @author william
 */
public class StValExistenciaPeca implements IStrategy{

    @Override
    //checa no banco de dados se a peça existe qdo fazer cadastro de pedido da peça
    public String processar(EntidadeDominio entidade) {
        
        IDAO dao = new DAOPeca();
        //caso dados não foram encontrados
        Pedido pedido = (Pedido) entidade;
        Peca peca = new Peca();
        peca.setId(pedido.getPeca().getId());
        
        String msg="";
        if (dao.consultar(peca).isEmpty()) {
            msg = "Peça não existe. ";
        } //caso dados foram encontrados
        return msg;
    }
    
}
