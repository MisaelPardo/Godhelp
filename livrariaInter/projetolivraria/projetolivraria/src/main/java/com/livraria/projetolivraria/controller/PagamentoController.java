package com.livraria.projetolivraria.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.livraria.projetolivraria.entities.PagamentoEntity;
import com.livraria.projetolivraria.service.PagamentoService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("lista-pagamentos");
        List<PagamentoEntity> lista = pagamentoService.listarTodos();
        mv.addObject("pagamentos", lista);
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute PagamentoEntity pagamento) {
        pagamentoService.salvar(pagamento);
        return "redirect:/pagamentos";
    }
}