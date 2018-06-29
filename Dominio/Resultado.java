/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public class Resultado extends EntidadeAplicacao{
    private String msg;
    private List<EntidadeDominio> entidades = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<EntidadeDominio> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadeDominio> entidades) {
        this.entidades = entidades;
    }
    
    
    
    
}
