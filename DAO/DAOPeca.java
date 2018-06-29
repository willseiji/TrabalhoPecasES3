/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Dominio.EntidadeDominio;
import Dominio.Peca;
import Dominio.Estoque;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author william
 */
public class DAOPeca implements IDAO {

    //método salvar DAO para Peça
    @Override
    public void salvar(EntidadeDominio entidade) {
        //cast da Peça
        Peca p = (Peca) entidade;
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt = null;
        //comando SQL a ser feito
        String sql = "INSERT INTO peca(idPeca,descricao, tamanho, peso,material,qtdeEstoque) VALUES(?,?,?,?,?,?)";
        //determinando valores a serem encontrados no banco de dados
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getId());
            stmt.setString(2, p.getNmPeca());
            stmt.setString(3, p.getTamanho());
            stmt.setDouble(4, p.getPeso());
            stmt.setString(5, p.getMaterial());
            stmt.setInt(6, p.getEstoque().getQtdeEstoque());
            stmt.executeUpdate();
            System.out.println("Salvo com sucesso");
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao salvar:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    //método consultar DAO para Peça
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt = null;
        //criando result de dados obtidos de banco de dados
        ResultSet rs = null;
        //criando list de dados lidos
        List<EntidadeDominio> pecas = new ArrayList<>();
        //cast de Peca
        Peca p = (Peca) entidade;
        //comando SQL a ser feito
        String sql = "SELECT * FROM peca WHERE idPeca = ? OR descricao =?";
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getId());
            stmt.setString(2, p.getNmPeca());
            rs = stmt.executeQuery();
            //lendo vários dados
            while (rs.next()) {
                Peca peca = new Peca();
                Estoque estoque = new Estoque();
                peca.setId(rs.getString("idPeca"));
                peca.setNmPeca(rs.getString("descricao"));
                peca.setTamanho(rs.getString("tamanho"));
                peca.setPeso(rs.getDouble("peso"));
                peca.setMaterial(rs.getString("material"));
                estoque.setQtdeEstoque(rs.getInt("qtdeEstoque"));
                peca.setEstoque(estoque);
                pecas.add(peca);
            }
        } catch (SQLException ex) {
            System.out.println("falha de leitura");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        //retornando dados lidos
        return pecas;
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        //cast da Peça
        Peca p = (Peca) entidade;
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt = null;
        //comando SQL a ser feito
        String sql = "UPDATE peca SET descricao=?, tamanho=?, peso=?,material=?,qtdeEstoque=? WHERE idPeca=?";
        //determinando valores a serem encontrados no banco de dados
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getNmPeca());
            stmt.setString(2, p.getTamanho());
            stmt.setDouble(3, p.getPeso());
            stmt.setString(4, p.getMaterial());
            stmt.setInt(5, p.getEstoque().getQtdeEstoque());
            stmt.setString(6, p.getId());
            stmt.executeUpdate();
            System.out.println("Salvo com sucesso");
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao salvar:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt=null;
        ResultSet rs = null;
        Peca p = (Peca) entidade;
        //comando SQL a ser feito
        String sql = "DELETE FROM peca WHERE idPeca=?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getId());
            stmt.executeUpdate();
            System.out.println("excluido com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
