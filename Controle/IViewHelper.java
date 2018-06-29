/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Dominio.EntidadeDominio;
import Dominio.Resultado;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author william
 */
public interface IViewHelper {
    public EntidadeDominio getEntidade(HttpServletRequest request);
    public void setView(Resultado resultado,HttpServletRequest request,HttpServletResponse response);
}
