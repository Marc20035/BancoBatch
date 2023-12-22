package io.bootify.transacciones_bancarias_batch.controller;

import io.bootify.transacciones_bancarias_batch.model.LoteProcesamientoDTO;
import io.bootify.transacciones_bancarias_batch.service.LoteProcesamientoService;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/loteProcesamientos")
public class LoteProcesamientoController {

    private final LoteProcesamientoService loteProcesamientoService;

    public LoteProcesamientoController(final LoteProcesamientoService loteProcesamientoService) {
        this.loteProcesamientoService = loteProcesamientoService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loteProcesamientoes", loteProcesamientoService.findAll());
        return "loteProcesamiento/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("loteProcesamiento") final LoteProcesamientoDTO loteProcesamientoDTO) {
        return "loteProcesamiento/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("loteProcesamiento") @Valid final LoteProcesamientoDTO loteProcesamientoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loteProcesamiento/add";
        }
        loteProcesamientoService.create(loteProcesamientoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loteProcesamiento.create.success"));
        return "redirect:/loteProcesamientos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loteProcesamiento", loteProcesamientoService.get(id));
        return "loteProcesamiento/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("loteProcesamiento") @Valid final LoteProcesamientoDTO loteProcesamientoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loteProcesamiento/edit";
        }
        loteProcesamientoService.update(id, loteProcesamientoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loteProcesamiento.update.success"));
        return "redirect:/loteProcesamientos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = loteProcesamientoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            loteProcesamientoService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loteProcesamiento.delete.success"));
        }
        return "redirect:/loteProcesamientos";
    }

}
