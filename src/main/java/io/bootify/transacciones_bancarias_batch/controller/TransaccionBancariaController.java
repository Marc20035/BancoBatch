package io.bootify.transacciones_bancarias_batch.controller;

import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import io.bootify.transacciones_bancarias_batch.domain.LoteProcesamiento;
import io.bootify.transacciones_bancarias_batch.model.TransaccionBancariaDTO;
import io.bootify.transacciones_bancarias_batch.repos.CuentaBancariaRepository;
import io.bootify.transacciones_bancarias_batch.repos.LoteProcesamientoRepository;
import io.bootify.transacciones_bancarias_batch.service.TransaccionBancariaService;
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
@RequestMapping("/transaccionBancarias")
public class TransaccionBancariaController {

    private final TransaccionBancariaService transaccionBancariaService;
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final LoteProcesamientoRepository loteProcesamientoRepository;

    public TransaccionBancariaController(
            final TransaccionBancariaService transaccionBancariaService,
            final CuentaBancariaRepository cuentaBancariaRepository,
            final LoteProcesamientoRepository loteProcesamientoRepository) {
        this.transaccionBancariaService = transaccionBancariaService;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
        this.loteProcesamientoRepository = loteProcesamientoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("cuentaBancariaIdValues", cuentaBancariaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CuentaBancaria::getId, CuentaBancaria::getNumerocuenta)));
        model.addAttribute("loteProcesamientoIdValues", loteProcesamientoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoteProcesamiento::getId, LoteProcesamiento::getEstado)));
        model.addAttribute("cuentaBancariaId2Values", cuentaBancariaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CuentaBancaria::getId, CuentaBancaria::getNumerocuenta)));
        model.addAttribute("loteProcesamientoId3Values", loteProcesamientoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoteProcesamiento::getId, LoteProcesamiento::getEstado)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("transaccionBancarias", transaccionBancariaService.findAll());
        return "transaccionBancaria/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("transaccionBancaria") final TransaccionBancariaDTO transaccionBancariaDTO) {
        return "transaccionBancaria/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("transaccionBancaria") @Valid final TransaccionBancariaDTO transaccionBancariaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaccionBancaria/add";
        }
        transaccionBancariaService.create(transaccionBancariaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaccionBancaria.create.success"));
        return "redirect:/transaccionBancarias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("transaccionBancaria", transaccionBancariaService.get(id));
        return "transaccionBancaria/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("transaccionBancaria") @Valid final TransaccionBancariaDTO transaccionBancariaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaccionBancaria/edit";
        }
        transaccionBancariaService.update(id, transaccionBancariaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaccionBancaria.update.success"));
        return "redirect:/transaccionBancarias";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = transaccionBancariaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            transaccionBancariaService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("transaccionBancaria.delete.success"));
        }
        return "redirect:/transaccionBancarias";
    }

}
