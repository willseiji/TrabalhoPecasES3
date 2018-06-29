/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dominio.EntidadeDominio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author william
 */
//complementando data de cadastro
public class StComplDtCadastro implements IStrategy {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public String processar(EntidadeDominio entidade) {
        Date date = new Date();
        entidade.setDate(sdf.format(date));
        return "";
    }
    
}
