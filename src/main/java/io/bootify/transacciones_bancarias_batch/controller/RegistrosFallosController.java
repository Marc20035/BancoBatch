package io.bootify.transacciones_bancarias_batch.controller;

import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import io.bootify.transacciones_bancarias_batch.model.RegistrosFallosDTO;
import io.bootify.transacciones_bancarias_batch.repos.TransaccionBancariaRepository;
import io.bootify.transacciones_bancarias_batch.service.RegistrosFallosService;
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
@RequestMapping("/registrosFalloss")
public class RegistrosFallosController {

    private final RegistrosFallosService registrosFallosService;
    private final TransaccionBancariaRepository transaccionBancariaRepository;

    public RegistrosFallosController(final RegistrosFallosService registrosFallosService,
            final TransaccionBancariaRepository transaccionBancariaRepository) {
        this.registrosFallosService = registrosFallosService;
        this.transaccionBancariaRepository = transaccionBancariaRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("transaccionBancariaIdValues", transaccionBancariaRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TransaccionBancaria::getId, TransaccionBancaria::getDetalles)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("registrosFalloses", registrosFallosService.findAll());
        return "registrosFallos/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("registrosFallos") final RegistrosFallosDTO registrosFallosDTO) {
        return "registrosFallos/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("registrosFallos") @Valid final RegistrosFallosDTO registrosFallosDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("transaccionBancariaId") && registrosFallosDTO.getTransaccionBancariaId() != null && registrosFallosService.transaccionBancariaIdExists(registrosFallosDTO.getTransaccionBancariaId())) {
            bindingResult.rejectValue("transaccionBancariaId", "Exists.registrosFallos.transaccionBancariaId");
        }
        if (bindingResult.hasErrors()) {
            return "registrosFallos/add";
        }
        registrosFallosService.create(registrosFallosDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("registrosFallos.create.success"));
        return "redirect:/registrosFalloss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("registrosFallos", registrosFallosService.get(id));
        return "registrosFallos/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("registrosFallos") @Valid final RegistrosFallosDTO registrosFallosDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final RegistrosFallosDTO currentRegistrosFallosDTO = registrosFallosService.get(id);
        if (!bindingResult.hasFieldErrors("transaccionBancariaId") && registrosFallosDTO.getTransaccionBancariaId() != null &&
                !registrosFallosDTO.getTransaccionBancariaId().equals(currentRegistrosFallosDTO.getTransaccionBancariaId()) &&
                registrosFallosService.transaccionBancariaIdExists(registrosFallosDTO.getTransaccionBancariaId())) {
            bindingResult.rejectValue("transaccionBancariaId", "Exists.registrosFallos.transaccionBancariaId");
        }
        if (bindingResult.hasErrors()) {
            return "registrosFallos/edit";
        }
        registrosFallosService.update(id, registrosFallosDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("registrosFallos.update.success"));
        return "redirect:/registrosFalloss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        registrosFallosService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("registrosFallos.delete.success"));
        return "redirect:/registrosFalloss";
    }

}
