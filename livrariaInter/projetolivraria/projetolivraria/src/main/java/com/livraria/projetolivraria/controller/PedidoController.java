package com.livraria.projetolivraria.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import lombok.RequiredArgsConstructor;

import com.livraria.projetolivraria.entities.PedidoEntity;
import com.livraria.projetolivraria.entities.ClienteEntity;
import com.livraria.projetolivraria.entities.LivroEntity;
import com.livraria.projetolivraria.entities.LivroPedidoEntity;
import com.livraria.projetolivraria.entities.PagamentoEntity;

import com.livraria.projetolivraria.service.PedidoService;
import com.livraria.projetolivraria.service.ClienteService;
import com.livraria.projetolivraria.service.LivroService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final LivroService livroService;

    @GetMapping
    public ModelAndView listar(@RequestParam(required = false) String busca) {
        List<PedidoEntity> pedidos = pedidoService.listarTodos(busca);
        for (PedidoEntity p : pedidos) {
            int soma = 0;
            if (p.getItens() != null) {
                for (LivroPedidoEntity item : p.getItens()) {
                    soma += item.getQuantidade();
                }
            }
            p.setTotalItens(soma);
        }
        ModelAndView mv = new ModelAndView("lista-pedidos");
        mv.addObject("pedidos", pedidos);
        mv.addObject("busca", busca);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setDataPedido(LocalDate.now());
        pedido.setItens(new ArrayList<>());
        pedido.setPagamentos(new ArrayList<>());

        PagamentoEntity pag = new PagamentoEntity();
        pag.setStatus("PENDENTE"); 
        pag.setFormaPagamento("A definir");
        pedido.getPagamentos().add(pag);

        ModelAndView mv = new ModelAndView("cadastra-pedidos");
        mv.addObject("pedido", pedido);
        mv.addObject("listaClientes", clienteService.listarTodos(null));
        mv.addObject("listaLivros", livroService.listarTodos(null));
        return mv;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute PedidoEntity pedido, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/pedidos");

        if (pedido.getItens() == null) pedido.setItens(new ArrayList<>());

        // 1 Validar estoque antes de qualquer lógica
        for (LivroPedidoEntity item : pedido.getItens()) {
            if (item.getLivro() != null && item.getQuantidade() != null) {
                String isbn = item.getLivro().getIsbnLivro();
                boolean temEstoque = livroService.verificarDisponibilidade(isbn, item.getQuantidade());
                
                if (!temEstoque) {
                    ModelAndView mvErro = new ModelAndView("cadastra-pedidos");
                    mvErro.addObject("pedido", pedido);
                    mvErro.addObject("listaClientes", clienteService.listarTodos(null));
                    mvErro.addObject("listaLivros", livroService.listarTodos(null));
                    mvErro.addObject("erroEstoque", "Estoque insuficiente para o livro de ISBN: " + isbn);
                    return mvErro;
                }
            }
        }

        if (pedido.getClienteId() != null) {
            ClienteEntity cli = clienteService.buscarPorId(pedido.getClienteId()).orElse(null);
            pedido.setCliente(cli);
        }

        BigDecimal total = BigDecimal.ZERO;

        for (LivroPedidoEntity item : pedido.getItens()) {
            item.setPedido(pedido);
            if (item.getQuantidade() == null || item.getQuantidade() <= 0) item.setQuantidade(1);
            
            if (item.getLivro() != null) {
                String isbn = item.getLivro().getIsbnLivro();
                LivroEntity livro = livroService.buscarPorId(isbn).orElse(null);
                item.setLivro(livro);
                if (livro != null && livro.getPreco() != null) {
                    total = total.add(livro.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
                }
            }
        }

        pedido.setTotal(total.setScale(2, RoundingMode.HALF_UP));

        if (pedido.getPagamentos() != null) {
            for (PagamentoEntity pag : pedido.getPagamentos()) {
                pag.setPedido(pedido);
                pag.setCliente(pedido.getCliente());
                pag.setValor(total);
                if (pag.getFormaPagamento() == null || pag.getFormaPagamento().isBlank()) {
                    pag.setFormaPagamento("A definir");
                }
                if ("PAGO".equalsIgnoreCase(pag.getStatus())) {
                    pag.setDataPagamento(LocalDate.now());
                }
            }
        }

        // 2. SALVAR PEDIDO
        pedidoService.salvar(pedido);

        // 3. BAIXA NO ESTOQUE (Só acontece se o pedido for salvo com sucesso)
        for (LivroPedidoEntity item : pedido.getItens()) {
             livroService.baixarEstoque(item.getLivro().getIsbnLivro(), item.getQuantidade());
        }

        return mv;
    }

    @PostMapping("/atualizarStatus/{id}")
    public String atualizarStatus(@PathVariable("id") Integer id, @RequestParam("status") String status) {
        pedidoService.atualizarStatus(id, status);
        return "redirect:/pedidos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable("id") Integer id) {
        pedidoService.deletar(id);
        return "redirect:/pedidos";
    }
}