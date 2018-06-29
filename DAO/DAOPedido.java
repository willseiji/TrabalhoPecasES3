/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Dominio.EntidadeDominio;
import Dominio.Funcionario;
import Dominio.Peca;
import Dominio.Pedido;
import Dominio.Setor;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author william
 */
public class DAOPedido implements IDAO {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //método salvar DAO para Pedido
    @Override
    public void salvar(EntidadeDominio entidade) {
        //cast do Pedido
        Pedido p = (Pedido) entidade;
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt = null;
        //comando SQL a ser feito
        String sql = "INSERT INTO pedido(idPeca, qtdePeca, setor,dtPedido,nmFuncionario) VALUES(?,?,?,?,?)";
        //determinando valores a serem encontrados no banco de dados
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getPeca().getId());
            stmt.setInt(2, p.getQtde());
            stmt.setString(3, p.getSetor().getNmSetor());
            stmt.setString(4, entidade.getDate());
            stmt.setString(5, p.getFuncionario().getNome());
            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            System.out.println("salvo sucesso");
        } catch (SQLException ex) {
            System.out.println("erro ao salvar");
            //JOptionPane.showMessageDialog(null, "Erro ao salvar:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    //método consultar DAO para Pedido
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt=null;
        //criando result de dados obtidos de banco de dados
        ResultSet rs = null;
        //criando list de dados lidos
        List<EntidadeDominio> pedidos = new ArrayList<>();
        //cast de Pedido
        Pedido p = (Pedido) entidade;
        //comando SQL a ser feito
        String sql = "SELECT * FROM pedido INNER JOIN peca ON "
                + "pedido.idPeca = peca.idPeca WHERE pedido.idPedido = ? OR pedido.idPeca=? OR peca.descricao=?";
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getId());
            stmt.setString(2, p.getPeca().getId());
            stmt.setString(3, p.getPeca().getNmPeca());
            rs = stmt.executeQuery();
            //lendo vários dados
            while(rs.next()){
                Setor setor = new Setor();
                Peca peca = new Peca();
                Funcionario funcionario = new Funcionario();
                setor.setNmSetor(rs.getString("setor"));
                peca.setId(rs.getString("idPeca"));
                peca.setNmPeca(rs.getString("descricao"));
                peca.setTamanho(rs.getString("tamanho"));
                peca.setPeso(Double.parseDouble(rs.getString("peso")));
                peca.setMaterial(rs.getString("material"));
                funcionario.setNome(rs.getString("nmFuncionario"));
                int qtde=rs.getInt("qtdePeca");
                Pedido pedido = new Pedido(peca, qtde,setor,funcionario);
                pedido.setId(Integer.toString(rs.getInt("idPedido")));
                pedido.setDate(rs.getString("dtPedido"));
                pedidos.add(pedido);                  
            }
        } catch (SQLException ex) {
            System.out.println(p.getPeca().getNmPeca() +" nao encontrada");
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        //retornando dados lidos
        return pedidos;
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        //cast do Pedido
        Pedido p = (Pedido) entidade;
        //fazendo conexão com Factory
        Connection con = ConnectionFactory.getConnection();
        //criando statemment
        PreparedStatement stmt = null;
        //comando SQL a ser feito
        String sql = "UPDATE pedido SET qtdePeca=?, setor=?,nmFuncionario=?,dtPedido=? WHERE idPedido=?";
        //determinando valores a serem encontrados no banco de dados
        try {
            //comando inserido no SGBD
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getQtde());
            stmt.setString(2, p.getSetor().getNmSetor());
            stmt.setString(3, p.getFuncionario().getNome());
            stmt.setString(4, entidade.getDate());
            stmt.setInt(5, Integer.parseInt(p.getId()));
            
            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            System.out.println("salvo sucesso");
        } catch (SQLException ex) {
            System.out.println("erro ao salvar");
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
        Pedido p = (Pedido) entidade;
        //comando SQL a ser feito
        String sql = "DELETE FROM pedido WHERE idPedido=?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(p.getId()));
            stmt.executeUpdate();
            System.out.println("excluido com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
