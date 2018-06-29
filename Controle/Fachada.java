/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DAO.DAOPeca;
import DAO.DAOPedido;
import DAO.IDAO;
import Dominio.EntidadeDominio;
import Dominio.Peca;
import Dominio.Pedido;
import Dominio.Resultado;
import Negocio.IStrategy;
import Negocio.StChecarExistenciaCodigo;
import Negocio.StComplDtCadastro;
import Negocio.StDarBaixaEstoque;
import Negocio.StValCodigoPeca;
import Negocio.StValDadosPeca;
import Negocio.StValDadosPedido;
import Negocio.StValExistenciaPeca;
import Negocio.StValPeso;
import Negocio.StValQtdePeca;
import Negocio.StValQtdePedidoEstoque;

/**
 *
 * @author william
 */
public class Fachada implements IFachada {

    private Map<String, Map<String, List<IStrategy>>> rns;
    //private Map<String, List<IStrategy>> rns;
    private Map<String, IDAO> daos;
    Resultado resultado = new Resultado();

    public Fachada() {

        //strategies para peça
        StValCodigoPeca vCodigoPeca = new StValCodigoPeca();
        StValDadosPeca vDadosPeca = new StValDadosPeca();
        StValQtdePeca vQtdePeca = new StValQtdePeca();
        StValPeso vPesoPeca = new StValPeso();
        StChecarExistenciaCodigo vExisteCodigo = new StChecarExistenciaCodigo();

        //strategies para pedido
        StValExistenciaPeca vExistePeca = new StValExistenciaPeca();
        StValDadosPedido vDadosPedido = new StValDadosPedido();
        StValQtdePedidoEstoque vQtdePedido = new StValQtdePedidoEstoque();
        StComplDtCadastro vData = new StComplDtCadastro();
        StDarBaixaEstoque vDarBaixa = new StDarBaixaEstoque();

        //cria lista de regras de negocio para salvar Peça
        List<IStrategy> rnsPecaSalvar = new ArrayList<>();
        //adicionando Strategies na lista de regras de negócio da Peça
        rnsPecaSalvar.add(vCodigoPeca);
        rnsPecaSalvar.add(vExisteCodigo);
        rnsPecaSalvar.add(vDadosPeca);
        rnsPecaSalvar.add(vQtdePeca);
        rnsPecaSalvar.add(vPesoPeca);
        rnsPecaSalvar.add(vData);

        //cria lista de regras de negocio para salvar Pedido
        List<IStrategy> rnsPedidoSalvar = new ArrayList<>();
        //adicionando Strategies na lista de regras de negócio do Pedido
        rnsPedidoSalvar.add(vExistePeca);
        rnsPedidoSalvar.add(vQtdePedido);
        rnsPedidoSalvar.add(vDadosPedido);
        rnsPedidoSalvar.add(vData);
        rnsPedidoSalvar.add(vDarBaixa);

        //cria lista de regras de negocio para alterar Peça
        List<IStrategy> rnsPecaAlterar = new ArrayList<>();
        //adicionando Strategies na lista de regras de negócio da Peça Alterar
        rnsPecaAlterar.add(vDadosPeca);
        rnsPecaAlterar.add(vQtdePeca);
        rnsPecaAlterar.add(vPesoPeca);

        //cria lista de regras de negocio para alterar Pedido
        List<IStrategy> rnsPedidoAlterar = new ArrayList<>();
        //adicionando Strategies na lista de regras de negócio do Pedido Alterar
        rnsPedidoAlterar.add(vQtdePedido);
        rnsPedidoAlterar.add(vDadosPedido);
        rnsPedidoAlterar.add(vData);

        //cria lista de estrategies para demais operações de Peça e Pedido       
        List<IStrategy> rnsPecaPesquisar = new ArrayList<>();
        List<IStrategy> rnsPecaExcluir = new ArrayList<>();
        List<IStrategy> rnsPedidoPesquisar = new ArrayList<>();
        List<IStrategy> rnsPedidoExcluir = new ArrayList<>();

        //mapeando classes com lista de estrategies
        Map<String, List<IStrategy>> rnsPeca = new HashMap<>();
        Map<String, List<IStrategy>> rnsPedido = new HashMap<>();

        //inserindo mapa de regras de negocios de acordo com suas operações
        rnsPeca.put("SALVAR", rnsPecaSalvar);
        rnsPedido.put("SALVAR", rnsPedidoSalvar);
        rnsPeca.put("ALTERAR", rnsPecaAlterar);
        rnsPedido.put("ALTERAR", rnsPedidoAlterar);
        rnsPeca.put("EXCLUIR", rnsPecaExcluir);
        rnsPedido.put("EXCLUIR", rnsPedidoExcluir);
        rnsPeca.put("PESQUISAR", rnsPecaPesquisar);
        rnsPedido.put("PESQUISAR", rnsPedidoPesquisar);

        //rns = new HashMap<String, List<IStrategy>>();
        rns = new HashMap<String, Map<String, List<IStrategy>>>();
        rns.put(Peca.class.getName(), rnsPeca);
        rns.put(Pedido.class.getName(), rnsPedido);

        //instanciando DAOs
        IDAO daoPeca = new DAOPeca();
        IDAO daoPedido = new DAOPedido();

        //mapeando classes com DAOs
        daos = new HashMap<String, IDAO>();
        daos.put(Peca.class.getName(), daoPeca);
        daos.put(Pedido.class.getName(), daoPedido);
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        // identificando nome da classe
        String nmClass = entidade.getClass().getName();
        //executando validações de regras de negocio        
        String msg = executarRegras(entidade, "SALVAR");
        //chamando DAO em caso de todas as validações atendidas
        if (msg.isEmpty()) {
            IDAO dao = daos.get(nmClass);
            dao.salvar(entidade);
                resultado.setMsg("Dados salvos no Banco de Dados");
                List<EntidadeDominio> entidades = new ArrayList<>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
            // retornando msg de falha de validação sem poder acessar o DAO
        } else {
            resultado.setMsg(msg.toString());
        }
        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {
        // identificando nome da classe
        String nmClass = entidade.getClass().getName();
        // instanciando DAO de acordo com a classe
        IDAO dao = daos.get(nmClass);
        //executando validações de regras de negocio        
        String msg = executarRegras(entidade, "PESQUISAR");
        if (msg.isEmpty()) {
            if (dao.consultar(entidade).isEmpty()) {
                //caso dados não foram encontrados
                resultado.setMsg("dados nao encontrados");
            } else {
                //caso dados foram encontrados   
                resultado.setMsg("pesquisa feita com sucesso");
            }
            resultado.setEntidades(dao.consultar(entidade));
        } else {
            resultado.setMsg(msg.toString());
        }
        return resultado;
    }

    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        // identificando nome da classe
        String nmClass = entidade.getClass().getName();

        //executando validações de regras de negocio        
        String msg = executarRegras(entidade, "ALTERAR");

        //chamando DAO em caso de todas as validações atendidas
        if (msg.isEmpty()) {
            IDAO dao = daos.get(nmClass);
            dao.alterar(entidade);
            resultado.setMsg("Dados alterados");
        } else {
            resultado.setMsg(msg.toString());
        }
        return resultado;
    }

    @Override
    public Resultado excluir(EntidadeDominio entidade) {
        // identificando nome da classe
        String nmClass = entidade.getClass().getName();
        //executando validações de regras de negocio        
        String msg = executarRegras(entidade, "EXCLUIR");
        if (msg.isEmpty()) {
            IDAO dao = daos.get(nmClass);
            dao.excluir(entidade);
            resultado.setMsg("Dados excluidos");
        } else {
            resultado.setMsg(msg.toString());
        }
        return resultado;
    }

    private String executarRegras(EntidadeDominio entidade, String operacao) {
        // identificando nome da classe
        String nmClasse = entidade.getClass().getName();
        //para construção de String de mensagens
        StringBuilder msg = new StringBuilder();
        //mapeando classes com lista de estrategies
        Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);
        //caso tenha regras de negocio associada à classe
        if (!regrasOperacao.isEmpty()) {
            List<IStrategy> regras = regrasOperacao.get(operacao);
            //caso tenha regras de negocio associada à operação da classe
            if (!regras.isEmpty()) {
                for (IStrategy s : regras) {
                    //checando mensagens de regras de negocio
                    String m = s.processar(entidade);
                    if (!m.isEmpty()) {
                        //adicionando mensagens na StringBuilder
                        msg.append(m);
                    }
                }
            }
        }
        if (msg.length() > 0) {
            return msg.toString();
        } else {
            return "";
        }
    }

}
