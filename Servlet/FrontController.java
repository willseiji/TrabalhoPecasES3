/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.AlterarCommand;
import Controle.ExcluirCommand;
import Controle.ICommand;
import Controle.IViewHelper;
import Controle.PecaViewHelper;
import Controle.PedidoViewHelper;
import Controle.PesquisarCommand;
import Controle.SalvarCommand;

import Dominio.EntidadeDominio;
import Dominio.Resultado;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author william
 */
@WebServlet(name = "FrontController")
public class FrontController extends HttpServlet {

    private static Map<String, IViewHelper> vhs;
    private static Map<String, ICommand> commands;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public FrontController() {
        //mapeando URI com tipo de instância para View Helper
        vhs = new HashMap<String, IViewHelper>();
        vhs.put("/TrabalhoPecasES3/CadastrarPeca", new PecaViewHelper());
        vhs.put("/TrabalhoPecasES3/CadastrarPedido", new PedidoViewHelper());
        vhs.put("/TrabalhoPecasES3/PesquisarPeca", new PecaViewHelper());
        vhs.put("/TrabalhoPecasES3/PesquisarPedido", new PedidoViewHelper());
        vhs.put("/TrabalhoPecasES3/ExcluirPeca", new PecaViewHelper());
        vhs.put("/TrabalhoPecasES3/ExcluirPedido", new PedidoViewHelper());
        vhs.put("/TrabalhoPecasES3/AlterarPeca", new PecaViewHelper());
        vhs.put("/TrabalhoPecasES3/AlterarPedido", new PedidoViewHelper());

        //mapeando 'operação' com instância para Command
        commands = new HashMap<String, ICommand>();
        commands.put("SALVAR", new SalvarCommand());
        commands.put("PESQUISAR", new PesquisarCommand());
        commands.put("EXCLUIR", new ExcluirCommand());
        commands.put("ALTERAR", new AlterarCommand());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    //      try {
        
        //obtém nome do identificar do recurso (URI)
        String uri = request.getRequestURI();
        //cria instância de view helper de acordo com recurso de origem
        IViewHelper vh = vhs.get(uri);
        //obtém nome da operação de acordo com o 'value' do botão que foi apertado 
        String operacao = request.getParameter("operacao");
        //cria instância de tipo de command de acordo com identicação da operação
        ICommand command = commands.get(operacao);
        
        //recebe objeto da entidade domínio instanciada na chamada da view helper
        EntidadeDominio entidade = vh.getEntidade(request);
        
        //recebe mensagens e /ou lista de entidades de acordo com execução do tipo de command
        Resultado resultado = command.executar(entidade);
        //chama view helper para ajustar o tipo de dados a serem exibidos na view
        vh.setView(resultado, request, response);    
    //processRequest
//         request.getRequestDispatcher(page).forward(request, response);
    //} catch (DatabaseException he) {
      //  throw new ServletException(he);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
