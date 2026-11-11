package com.example.cmpact3_t4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cmpact3_t4.dto.AlumnoRequest;
import com.example.cmpact3_t4.service.AlumnoService;
import com.example.cmpact3_t4.service.CarreraService;

// CRUD con vistas Thymeleaf
@Controller
@RequestMapping("/alumnos")
public class AlumnoViewController {

    private final AlumnoService alumnoService;
    private final CarreraService carreraService;

    @Value("${app.nombre-proyecto}")
    private String nombreProyecto;

    public AlumnoViewController(AlumnoService alumnoService, CarreraService carreraService) {
        this.alumnoService = alumnoService;
        this.carreraService = carreraService;
    }

    @ModelAttribute
    public void datosComunes(Model model) {
        model.addAttribute("nombreProyecto", nombreProyecto);
    }

    // READ: lista de alumnos (con su carrera); filtro opcional por carrera
    @GetMapping
    public String listar(@RequestParam(required = false) Long carreraId, Model model) {
        model.addAttribute("alumnos", alumnoService.listar(carreraId));
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("carreraId", carreraId);
        return "alumnos/lista";
    }

    // CREATE (formulario vacío)
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        return formulario(new AlumnoRequest(), null, null, model);
    }

    // CREATE (guardar)
    @PostMapping
    public String crear(@ModelAttribute("alumno") AlumnoRequest datos, Model model, RedirectAttributes redirect) {
        try {
            alumnoService.crear(datos);
            redirect.addFlashAttribute("mensaje", "Alumno " + datos.getNombre() + " registrado correctamente.");
            return "redirect:/alumnos";
        } catch (IllegalArgumentException e) {
            return formulario(datos, null, e.getMessage(), model);
        }
    }

    // UPDATE (formulario con los datos actuales)
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return formulario(alumnoService.aFormulario(id), id, null, model);
    }

    // UPDATE (guardar cambios)
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("alumno") AlumnoRequest datos,
                             Model model, RedirectAttributes redirect) {
        try {
            alumnoService.actualizar(id, datos);
            redirect.addFlashAttribute("mensaje", "Alumno " + datos.getNombre() + " actualizado correctamente.");
            return "redirect:/alumnos";
        } catch (IllegalArgumentException e) {
            return formulario(datos, id, e.getMessage(), model);
        }
    }

    // DELETE
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        String nombre = alumnoService.buscar(id).getNombre();
        alumnoService.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Alumno " + nombre + " eliminado correctamente.");
        return "redirect:/alumnos";
    }

    private String formulario(AlumnoRequest datos, Long id, String error, Model model) {
        model.addAttribute("alumno", datos);
        model.addAttribute("alumnoId", id);
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("error", error);
        return "alumnos/formulario";
    }
}
