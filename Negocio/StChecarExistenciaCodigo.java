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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
//checa no banco de dados se código da peça já existe para que não haja tentativa de inserção de código repetido
public class StChecarExistenciaCodigo implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        Peca peca = (Peca) entidade;
        IDAO dao = new DAOPeca();
        List<EntidadeDominio> pecas = new ArrayList<>();
        String msg="";
        pecas = dao.consultar(entidade);
        for(int i=0;i<pecas.size();i++)
        {
            if(peca.getId().equals(pecas.get(i).getId()))
                msg = "Código já existe. ";
            else
                msg = "";
        }
        return msg;
    }
    
}
