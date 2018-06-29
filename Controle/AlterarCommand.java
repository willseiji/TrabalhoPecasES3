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
public class AlterarCommand implements ICommand{
    Fachada fachada = new Fachada();
    Resultado resultado = new Resultado();
    
    @Override
    public Resultado executar(EntidadeDominio entidade) {
        //chama fachada para alterar e retorna msg e/ou valores do banco de dados
        resultado = fachada.alterar(entidade);
        return resultado;
    }
    
}
