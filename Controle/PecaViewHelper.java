/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Dominio.EntidadeDominio;
import Dominio.Estoque;
import Dominio.Peca;
import Dominio.Resultado;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author william
 */
public class PecaViewHelper implements IViewHelper {

    //private Map<String, EntidadeDominio> entidades;
    Peca peca = new Peca();
    Estoque estoque = new Estoque();

    //criando objeto da classe Peça que receberá valores do formulário, será instanciado e retornará para o FrontController
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {

        peca.setId("");
        peca.setNmPeca("");
        peca.setTamanho("");
        peca.setPeso(0);
        peca.setMaterial("");
        estoque.setQtdeEstoque(0);
        //obtém nome da operação 
        String operacao = request.getParameter("operacao");
        //caso botão apertado foi o de value = SALVAR' ou value = 'ALTERAR'
        if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
            //recebe valor de idPeça no campo Codigo da Peca
            String idPeca = request.getParameter("txt_IdPeca");
            //recebe valor de descrição da peça no campo Descrição da Peça
            String nmPeca = request.getParameter("txt_NmPeca");
            //recebe valor tamanho da peça no campo Tamanho
            String tamanho = request.getParameter("txt_Tamanho");
            //recebe valor de peso da peça no campo Peso
            String peso = request.getParameter("txt_Peso");
            //recebe valor de material da peça no campo Material
            String material = request.getParameter("txt_Material");
            //recebe valor de qtde Estoque da peça no campo Qtde Estoque
            String qtdeEstoque = request.getParameter("txt_QtdeEstoque");

            //formatando dados de entrada obtido do formulário
            if (idPeca != null && !idPeca.trim().equals("")) {
                peca.setId(idPeca);
            }
            if (nmPeca != null && !nmPeca.trim().equals("")) {
                peca.setNmPeca(nmPeca);
            }
            if (tamanho != null && !tamanho.trim().equals("")) {
                peca.setTamanho(tamanho);
            }
            if (peso != null && !peso.trim().equals("")) {
                peca.setPeso(Double.parseDouble(peso));
            }
            if (material != null && !material.trim().equals("")) {
                peca.setMaterial(material);
            }
            if (qtdeEstoque != null && !qtdeEstoque.trim().equals("")) {
                estoque.setQtdeEstoque(Integer.parseInt(qtdeEstoque));
            }
            peca.setEstoque(estoque);
            //caso botão apertado foi o de 'value = PESQUISAR'
        } else if (operacao.equals("PESQUISAR")) {
            //recebendo valor de pesquisa
            String valor = request.getParameter("Valor");
            peca.setId(valor);
            peca.setNmPeca(valor);
        } else if (operacao.equals("EXCLUIR")) {
            String idPeca = request.getParameter("nmIdPeca");
            peca.setId(idPeca);
        }//else if
        return peca;
    }//

    //ajustando dados da Peça que serão enviados para a View da Peça
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) {

        //obtém nome da operação de acordo com o 'value' do botão que foi apertado 
        String operacao = request.getParameter("operacao");
        //recupera mensagem criada na fachada com texto de sucesso ou falha nas regras de negocio ou acesso ao DAO
        String msg = resultado.getMsg();
        //envia mensagem para o request
        request.setAttribute("msg", msg);
        //caso botão apertado foi o de 'value = SALVAR'
        if (operacao.equals("SALVAR")) {
            List<String> itensPeca = new java.util.ArrayList<>();
            List<List<String>> listaPecas = new java.util.ArrayList<>();
            response.setContentType("text/html;charset=UTF-8");
            itensPeca = new java.util.ArrayList<>();
            //Peca peca = (Peca) resultado.getEntidades().get(i);
            if (!resultado.getEntidades().isEmpty()) {
                Peca peca = (Peca) resultado.getEntidades().get(0);
                //criando lista de itens Peça para enviar na view
                itensPeca.add(peca.getId());
                itensPeca.add(peca.getNmPeca());
                itensPeca.add(peca.getTamanho());
                itensPeca.add(Double.toString(peca.getPeso()));
                itensPeca.add(peca.getMaterial());
                itensPeca.add(Integer.toString(peca.getEstoque().getQtdeEstoque()));
                request.setAttribute("itensPeca", itensPeca);
            }
            try {
                //redireciona para a pagina CadastrarPeca
                request.getRequestDispatcher("CadastrarPeca.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PecaViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            //caso botão apertado foi o de 'value = PESQUISAR'
            
            //caso botão apertado foi o de 'value = PESQUISAR'
        } else if (operacao.equals("PESQUISAR")) {
            List<String> itensPeca;
            List<List<String>> listaPecas = new java.util.ArrayList<>();
            int i;
            request.getSession().setAttribute("resultado", resultado);
            //caso a leitura no banco de dados não tenha encontrado nenhum dado

            if (resultado.getEntidades().isEmpty()) {
                request.setAttribute("listaPecas", listaPecas);
                try {
                    request.getRequestDispatcher("MenuPeca.jsp").forward(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(PecaViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                //caso a leitura no banco de dados tenha encontrado dados com sucesso
                
                //caso a leitura no banco de dados tenha encontrado dados com sucesso
            } else {
                for (i = 0; i < resultado.getEntidades().size(); i++) {
                    itensPeca = new java.util.ArrayList<>();
                    //Peca peca = (Peca) resultado.getEntidades().get(i);
                    Peca peca = (Peca) resultado.getEntidades().get(i);
                    //criando lista de itens Peça para enviar na view
                    itensPeca.add(peca.getId());
                    itensPeca.add(peca.getNmPeca());
                    itensPeca.add(peca.getTamanho());
                    itensPeca.add(Double.toString(peca.getPeso()));
                    itensPeca.add(peca.getMaterial());
                    itensPeca.add(Integer.toString(peca.getEstoque().getQtdeEstoque()));
                    listaPecas.add(itensPeca);
                }
                request.setAttribute("listaPecas", listaPecas);
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    //enviando dados lidos do banco de dados para o request
                    try {
                        request.getRequestDispatcher("MenuPeca.jsp").forward(request, response);
                    } catch (ServletException ex) {
                        Logger.getLogger(PecaViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PecaViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }//else
        } else if (operacao.equals("EXCLUIR")) {
            try {
                request.getRequestDispatcher("MenuPeca.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (operacao.equals("ALTERAR")) {
            List<String> itensPeca = new java.util.ArrayList<>();
            String nmPeca = request.getParameter("txt_NmPeca");
            String tamanho = request.getParameter("txt_Tamanho");
            String peso = request.getParameter("txt_Peso");
            String material = request.getParameter("txt_Material");
            String qtdeEstoque = request.getParameter("txt_QtdeEstoque");
            itensPeca.add(nmPeca);
            itensPeca.add(tamanho);
            itensPeca.add(peso);
            itensPeca.add(material);
            itensPeca.add(qtdeEstoque);
            request.setAttribute("itensPeca", itensPeca);

            try {
                request.getRequestDispatcher("AlterarPeca.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//else if
    }//set view
}
