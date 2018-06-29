/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Dominio.EntidadeDominio;
import Dominio.Resultado;


/**
 *
 * @author william
 */
public interface IFachada {
        public Resultado salvar(EntidadeDominio entidade);
	public Resultado consultar(EntidadeDominio entidade);
        public Resultado alterar(EntidadeDominio entidade);
	public Resultado excluir(EntidadeDominio entidade);
}
