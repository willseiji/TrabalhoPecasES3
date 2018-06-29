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
public interface ICommand {
    public Resultado executar(EntidadeDominio entidade);
}
