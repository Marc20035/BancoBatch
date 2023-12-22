package io.bootify.transacciones_bancarias_batch.controller;

import io.bootify.transacciones_bancarias_batch.domain.Cliente;
import io.bootify.transacciones_bancarias_batch.model.CuentaBancariaDTO;
import io.bootify.transacciones_bancarias_batch.repos.ClienteRepository;
import io.bootify.transacciones_bancarias_batch.service.CuentaBancariaService;
import io.bootify.transacciones_bancarias_batch.util.CustomCollectors;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/cuentaBancarias")
public class CuentaBancariaController {

    private final CuentaBancariaService cuentaBancariaService;
    private final ClienteRepository clienteRepository;

    public CuentaBancariaController(final CuentaBancariaService cuentaBancariaService,
            final ClienteRepository clienteRepository) {
        this.cuentaBancariaService = cuentaBancariaService;
        this.clienteRepository = clienteRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("clienteIdValues", clienteRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Cliente::getId, Cliente::getNombre)));
        model.addAttribute("clienteId2Values", clienteRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Cliente::getId, Cliente::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("cuentaBancarias", cuentaBancariaService.findAll());
        return "cuentaBancaria/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("cuentaBancaria") final CuentaBancariaDTO cuentaBancariaDTO) {
        return "cuentaBancaria/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("cuentaBancaria") @Valid final CuentaBancariaDTO cuentaBancariaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cuentaBancaria/add";
        }
        cuentaBancariaService.create(cuentaBancariaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cuentaBancaria.create.success"));
        return "redirect:/cuentaBancarias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("cuentaBancaria", cuentaBancariaService.get(id));
        return "cuentaBancaria/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("cuentaBancaria") @Valid final CuentaBancariaDTO cuentaBancariaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cuentaBancaria/edit";
        }
        cuentaBancariaService.update(id, cuentaBancariaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cuentaBancaria.update.success"));
        return "redirect:/cuentaBancarias";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = cuentaBancariaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            cuentaBancariaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("cuentaBancaria.delete.success"));
        }
        return "redirect:/cuentaBancarias";
    }

}
