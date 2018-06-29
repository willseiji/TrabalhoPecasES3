/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.DAOPeca;
import DAO.IDAO;
import Dominio.EntidadeDominio;
import Dominio.Estoque;
import Dominio.Peca;
import Dominio.Pedido;
import java.util.List;

/**
 *
 * @author william
 */
public class StDarBaixaEstoque implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        String msg;
        StValExistenciaPeca vExistePeca = new StValExistenciaPeca();

        if (vExistePeca.processar(entidade).isEmpty()) {
            Pedido pedido = (Pedido) entidade;
            pedido.getPeca().getId();
            Peca peca = new Peca();
            Estoque estoque = new Estoque();
            peca.setId(pedido.getPeca().getId());
            peca.setNmPeca(pedido.getPeca().getNmPeca());
            IDAO dao = new DAOPeca();
            List<EntidadeDominio> entidades = dao.consultar(peca);

            Peca pecas = (Peca) entidades.get(0);
            peca.setNmPeca(pecas.getNmPeca());
            peca.setTamanho(pecas.getTamanho());
            peca.setPeso(pecas.getPeso());
            peca.setMaterial(pecas.getMaterial());
            int qtde = pecas.getEstoque().getQtdeEstoque() - pedido.getQtde();
            if (qtde >= 0) {
                estoque.setQtdeEstoque(qtde);
                peca.setEstoque(estoque);
                dao.alterar(peca);
            }
        }
        return "";
    }

}
