/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Dominio.EntidadeDominio;
import Dominio.Funcionario;
import Dominio.Peca;
import Dominio.Pedido;
import Dominio.Resultado;
import Dominio.Setor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author william
 */
public class PedidoViewHelper implements IViewHelper {

    Peca peca = new Peca();
    Pedido pedido = new Pedido();
    Setor setor = new Setor();
    Funcionario funcionario = new Funcionario();
    int qtde;

    //criando objeto da classe Pedido que receberá valores do formulário, será instanciado e retornará para o FrontController
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {

        peca.setId("");
        pedido.setQtde(0);
        setor.setNmSetor("");
        funcionario.setNome("");
            
        //obtém nome da operação de acordo com o 'value' do botão que foi apertado 
        String operacao = request.getParameter("operacao");
        //caso botão apertado foi o de 'value = SALVAR'
        if (operacao.equals("SALVAR")) {
            //recebe valor de ID da peça no campo IDPeça
            String nmIdPeca = request.getParameter("txt_IdPeca");
            //recebe valor de quantidade da peça no campo Quantidade
            String qtde = request.getParameter("txt_Qtde");
            //recebe valor de nome do Setor que fez a requisição no campo Setor
            String nmSetor = (request.getParameter("txt_Setor"));
            //recebe valor de nome do Funcionario no campo Solicitante
            String nmFuncionario = (request.getParameter("txt_Funcionario"));
            //formatando dados de entrada obtido do formulário
            
            if (nmIdPeca != null && !nmIdPeca.trim().equals("")) {
                peca.setId(nmIdPeca);
            }
            if (qtde != null && !qtde.trim().equals("")) {
                int intQtde = Integer.parseInt(qtde);
                pedido.setQtde(intQtde);
            }
            if (nmSetor != null && !nmSetor.trim().equals("")) {
                setor.setNmSetor(nmSetor);
            }
            if (nmFuncionario != null && !nmFuncionario.trim().equals("")) {
                funcionario.setNome(nmFuncionario);
            }
            
            pedido.setPeca(peca);
            pedido.setSetor(setor);
            pedido.setFuncionario(funcionario);
            
            //caso botão apertado foi o de 'value = PESQUISAR'
        } else if (operacao.equals("PESQUISAR")) {
            pedido = new Pedido();
            //recebendo valor de pesquisa
            String valor = request.getParameter("Valor");
            if(valor.matches("^[0-9]*$")){
                pedido.setId(valor);
            }
            peca.setId(valor);
            peca.setNmPeca(valor);
            pedido.setPeca(peca);
            
        } else if (operacao.equals("EXCLUIR")) {
            String idPedido = request.getParameter("nmIdPedido");
            pedido.setId(idPedido);
    
        } else if (operacao.equals("ALTERAR")) {
            //recebe valor de idPedido
            String idPedido = request.getParameter("txt_IdPedido");
            //recebe valor de idPeca
            String idPeca = request.getParameter("txt_IdPeca");
            //recebe valor de quantidade da peça no campo Quantidade que foi definido com name = "txt_Qtde"
            String qtde = request.getParameter("txt_Qtde");
            //recebe valor de nome do Setor no campo Setor que foi definido com name = "txt_Setor"
            String nmSetor = (request.getParameter("txt_Setor"));
            //formatando dados de entrada obtido do formulário
            String nmFuncionario = (request.getParameter("txt_Funcionario"));
            //formatando dados de entrada obtido do formulário
            
            peca.setId(idPeca);
            pedido.setId(idPedido);
            if (qtde != null && !qtde.trim().equals("")) {
                int intQtde = Integer.parseInt(qtde);
                pedido.setQtde(intQtde);
            }
            if (nmSetor != null && !nmSetor.trim().equals("")) {
                setor.setNmSetor(nmSetor);
            }
            if (nmFuncionario != null && !nmFuncionario.trim().equals("")) {
                funcionario.setNome(nmFuncionario);
            }
            pedido.setPeca(peca);
            pedido.setSetor(setor);
            pedido.setFuncionario(funcionario);
            
        }//else if
        return pedido;
    }

    //ajustando dados do Pedido que serão enviados para a View do Pedido
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) {
        //obtém nome da operação
        String operacao = request.getParameter("operacao");
        //recupera mensagem criada na fachada com texto de sucesso ou falha nas regras de negocio ou acesso ao DAO
        String msg = resultado.getMsg();
        //envia mensagem para o request
        request.setAttribute("msg", msg);
        
        //caso botão apertado foi o de 'value = SALVAR'
        if (operacao.equals("SALVAR")) {
            List<String> itensPedido = new ArrayList<>();
            
            if (!resultado.getEntidades().isEmpty()) {
                    Pedido pedido = (Pedido) resultado.getEntidades().get(0);
                    //criando lista de itens Pedido para enviar na view
                    itensPedido.add(pedido.getId());
                    itensPedido.add(pedido.getPeca().getId());
                    itensPedido.add(pedido.getPeca().getNmPeca());
                    itensPedido.add(Integer.toString(pedido.getQtde()));
                    itensPedido.add(pedido.getSetor().getNmSetor());
                    itensPedido.add(pedido.getDate());
                    itensPedido.add(pedido.getFuncionario().getNome());
                    itensPedido.add(pedido.getPeca().getTamanho());
                    itensPedido.add(Double.toString(pedido.getPeca().getPeso()));
                    itensPedido.add(pedido.getPeca().getMaterial());
                    request.setAttribute("itensPedido",itensPedido);
                    System.out.println("itensPedido: "+itensPedido);
            }
            try {
                //redireciona para a pagina CadastrarPedido
                request.getRequestDispatcher("CadastrarPedido.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            //caso botão apertado foi o de 'value = PESQUISAR'
            

            //caso botão apertado foi o de 'value = PESQUISAR'
        } else if (operacao.equals("PESQUISAR")) {

            List<String> itensPedido = new ArrayList<>();
            List<List<String>> listaPedidos = new ArrayList<>();
            int i;
            //caso a leitura no banco de dados não tenha encontrado nenhum dado
            if (resultado.getEntidades().isEmpty()) {
                request.setAttribute("listaPedidos", listaPedidos);
                try {
                    request.getRequestDispatcher("MenuPedido.jsp").include(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(PecaViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                //caso a leitura no banco de dados tenha encontrado dados com sucesso
                
                //caso a leitura no banco de dados tenha encontrado dados com sucesso
            } else {
                //criando lista dados obtidos da leitura do banco de dados

                for (i = 0; i < resultado.getEntidades().size(); i++) {   
                    itensPedido = new ArrayList<>();
                    Pedido pedido = (Pedido) resultado.getEntidades().get(i);
                    //criando lista de itens Pedido para enviar na view
                    itensPedido.add(pedido.getId());
                    itensPedido.add(pedido.getPeca().getId());
                    itensPedido.add(pedido.getPeca().getNmPeca());
                    itensPedido.add(Integer.toString(pedido.getQtde()));
                    itensPedido.add(pedido.getSetor().getNmSetor());
                    itensPedido.add(pedido.getDate());
                    itensPedido.add(pedido.getFuncionario().getNome());
                    itensPedido.add(pedido.getPeca().getTamanho());
                    itensPedido.add(Double.toString(pedido.getPeca().getPeso()));
                    itensPedido.add(pedido.getPeca().getMaterial());
                    listaPedidos.add(itensPedido);                    
                }
                //enviando dados lidos do banco de dados para o request
                request.setAttribute("listaPedidos", listaPedidos);
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    try {
                        request.getRequestDispatcher("MenuPedido.jsp").forward(request, response);
                    } catch (ServletException ex) {
                        Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
                }        
            }//else
        } else if (operacao.equals("EXCLUIR")) {
            try {
                request.getRequestDispatcher("MenuPedido.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (operacao.equals("ALTERAR")) {
            List<String> itensPedido = new java.util.ArrayList<>();
            String qtde = request.getParameter("txt_Qtde");
            String nmSetor = (request.getParameter("txt_Setor"));
            String nmFuncionario = (request.getParameter("txt_Funcionario"));
            itensPedido.add(qtde);
            itensPedido.add(nmSetor);
            itensPedido.add(nmFuncionario);
            request.setAttribute("itensPedido", itensPedido);
            try {
                request.getRequestDispatcher("AlterarPedido.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(PedidoViewHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }//else if
    }//setView
}
