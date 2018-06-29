/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Dominio.EntidadeDominio;
import java.util.List;

/**
 *
 * @author william
 */
public interface IDAO {
        public void salvar(EntidadeDominio entidade);
	public void alterar(EntidadeDominio entidade);
	public List<EntidadeDominio> consultar(EntidadeDominio entidade);
	public void excluir(EntidadeDominio entidade);
}
