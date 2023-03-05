package springbootclientesApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springbootclientesApp.models.entity.Ciudad;
import springbootclientesApp.models.entity.Cliente;
import springbootclientesApp.models.service.ICiudadService;
import springbootclientesApp.models.service.IClienteService;

@Controller
@RequestMapping("/views/clientes")
public class ClientesController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private ICiudadService ciudadService;
	
	@GetMapping("/")
	public String listarClientes(Model model) {
		List <Cliente>listadoCliente=clienteService.listarTodos();
		
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", listadoCliente);

		return "/views/clientes/listar";
		
	}
	
	@GetMapping("/create")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		List<Ciudad>listCiudades = ciudadService.listaCiudades();
		
		model.addAttribute("titulo", "Formulario: Nuevo Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades",listCiudades);
		System.out.println(listCiudades);
		
		return "/views/clientes/formularioCrear";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, 
			Model model, RedirectAttributes attribute	) {
		List<Ciudad>listCiudades = ciudadService.listaCiudades();
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario: Nuevo Cliente");
			model.addAttribute("cliente", cliente);
			model.addAttribute("ciudades",listCiudades);
			System.out.println("Hubo errores en el formlario");
			//no se veria el mensaje flash
			return "/views/clientes/formularioCrear";
		}
		
		clienteService.guardar(cliente);
		System.out.println("Cliente Guardado con Exito");
		attribute.addFlashAttribute("success", "Cliente guardado con exito");
		System.out.println(cliente);
		return "redirect:/views/clientes/";
		
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idCliente, Model model, 
							RedirectAttributes attribute) {
	
		Cliente cliente = null;
		
		if(idCliente>0) {
			cliente=clienteService.buscarPorId(idCliente);
			
			if(cliente == null) {
				System.out.println("ERROR: El id del cliente no existe.");
				attribute.addFlashAttribute("error", "ATENCION: El id del cliente no existe.");
				return "redirect:/views/clientes/";
			}
			}else {
				System.out.println("ERROR: Error con id cliente.");
				attribute.addFlashAttribute("error", "ATENCION: Error con el id del cliente.");
				return "redirect:/views/clientes/";
			}
		
		List<Ciudad>listCiudades = ciudadService.listaCiudades();
		
		model.addAttribute("titulo", "Editar Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades",listCiudades);
		
		return "/views/clientes/formularioCrear";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCliente, RedirectAttributes attribute) {
		
		Cliente cliente = null;
		
		if(idCliente>0) {
			cliente=clienteService.buscarPorId(idCliente);
			
			if(cliente == null) {
				System.out.println("ERROR: El id del cliente no existe.");
				attribute.addFlashAttribute("error", "ATENCION: El id del cliente no existe.");
				return "redirect:/views/clientes/";
			}
			}else {
				System.out.println("ERROR: Error con id cliente.");
				attribute.addFlashAttribute("error", "ATENCION: Error con el id del cliente.");
				return "redirect:/views/clientes/";
			}
		
		
		clienteService.borrar(idCliente);
		System.out.println("Cliente " + idCliente + " borrado exitosamente.");
		attribute.addFlashAttribute("warning", "Registro eliminado exitosamente.");
		
		return "redirect:/views/clientes/";
	}
	
}
