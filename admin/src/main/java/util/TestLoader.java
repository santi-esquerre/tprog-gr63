package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datatypes.DTEventoAlta;
import datatypes.DTRegistrosOtorgados;
import datatypes.DTTipoRegistro;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IInstitucionController;
import interfaces.IUsuarioController;

import java.util.HashMap;
import java.util.HashSet;

public class TestLoader {
    private static final String COMMA_DELIMITER = ";";
    private static final String FILEPATH = "assets/tests";
    
    private enum FileName {
        CATEGORIAS("Categorias"),
        EDICIONES("EdicionesEventos"),
        INSTITUCIONES("Instituciones"),
    	PATROCINIOS("Patrocinios"),
    	REGISTROS("Registros"),
    	TIPOREGISTROS("TipoRegistro"),
    	USUARIOS("Usuarios"),
    	ASISTENTES("Usuarios-Asistentes"),
    	ORGANIZADORES("Usuarios-Organizadores"),
    	EVENTOS("Eventos");

        public final String label;

        private FileName(String label) {
            this.label = "2025" + label + ".csv";
        }
    }

    private static List<Map<String, String>> loadCsvWithHeader(String filePath) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return records;
            String[] headers = headerLine.split(COMMA_DELIMITER);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    record.put(headers[i], values[i]);
                }
                records.add(record);
            }
        }
        return records;
    }

    public void loadAll(String folderPath) throws IOException, ValidationInputException, UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException {
    	Factory factory = Factory.get();
    	IUsuarioController usuarioController = factory.getIUsuarioController();
    	IEventoController eventoController = factory.getIEventoController();
    	IEdicionController edicionController = factory.getIEdicionController();
    	IInstitucionController institucionController = factory.getIInstitucionController();
    	factory.getIRepository().reset();
        Map<String,List<Map<String, String>>> allRecords = new HashMap<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
            	allRecords.put(file.getName(), loadCsvWithHeader(file.getAbsolutePath()));
            }
        }
        
        //Carga de instituciones
        List<Map<String, String>> instituciones = allRecords.get(FileName.INSTITUCIONES.label);
        
        for (Map<String, String> institucion : instituciones) {
        	String nombre = institucion.get("Nombre");
        	String direccion = institucion.get("Descripcion");
        	String descripcion = institucion.get("SitioWeb");

            System.out.println(allRecords);
			institucionController.crearInstitucion(nombre, direccion, descripcion);
        }	
        // Carga de usuarios
		List<Map<String, String>> users = allRecords.get(FileName.USUARIOS.label);
		List<Map<String, String>> asistentes = allRecords.get(FileName.ASISTENTES.label);
		List<Map<String, String>> organizadores = allRecords.get(FileName.ORGANIZADORES.label);


		for (Map<String, String> user : users) {
			String nickname = user.get("Nickname");
			String nombre = user.get("Nombre");
			String email = user.get("Correo");
			if(user.get("Tipo").equals("O")) {
				Map<String, String> orga = organizadores.stream()
						.filter(o -> o.get("Ref").equals(user.get("Ref")))
						.findFirst()
						.orElse(null);
				
				String descripcion = orga.get("Descripcion");
				String linkSitioWeb = orga.get("LinkSitioWeb");
				usuarioController.crearOrganizador(nickname,
						nombre,
						email,
						descripcion,
						linkSitioWeb);
			}else if(user.get("Tipo").equals("A")) {
				Map<String, String> asist = asistentes.stream()
						.filter(a -> a.get("Ref").equals(user.get("Ref")))
						.findFirst()
						.orElse(null);

				Map<String, String> insti = instituciones.stream()
						.filter(i -> i.get("Ref").equals(asist.get("Institucion")))
						.findFirst()
						.orElse(null);
				
				String apellido = asist.get("Apellido");
				LocalDate fechaNac = LocalDate.parse(asist.get("FechaNacimiento"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				if (insti == null) {
					usuarioController.crearAsistente(nickname,
							nombre,
							apellido,
							email,
							fechaNac);
				}else {
					usuarioController.crearAsistente(nickname,
							nombre,
							apellido,
							email,
							fechaNac,
							insti.get("Nombre"));
				}

				
			}
				
			
		}
		
		// Carga de categorias
		List<Map<String, String>> categorias = allRecords.get(FileName.CATEGORIAS.label);
		for (Map<String, String> categoria : categorias) {
			if (categoria.get("Nombre") == null) continue;
			String nombre = categoria.get("Nombre");
			eventoController.altaCategoria(nombre);
		}
		
		// Carga de eventos
		List<Map<String, String>> eventos = allRecords.get(FileName.EVENTOS.label);
		for (Map<String, String> evento : eventos) {
			String nombre = evento.get("Nombre");
			String descripcion = evento.get("Descripcion");
			String sigla = evento.get("Sigla");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Date fechaAlta = Date.from(LocalDate.parse(evento.get("FechaAlta"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			Set<String> categoriasRefs = new HashSet<String>(Arrays.asList(evento.get("Categorias").split(",")) );
			Set<String> categoriasEvento = new HashSet<String>();
			for (String c : categoriasRefs) {
			    categoriasEvento.add(categorias.stream()
			    		.filter(cat -> cat.get("Ref").equals(c.trim()))
			    		.map(cat -> cat.get("Nombre"))
			    		.findFirst()
			    		.orElse(null));
			}
			
			DTEventoAlta dtEvento = new DTEventoAlta(nombre, descripcion,fechaAlta, sigla, categoriasEvento);
			eventoController.altaEvento(dtEvento);
		}
		
		
		// Carga de ediciones
		List<Map<String, String>> ediciones = allRecords.get(FileName.EDICIONES.label);
		for (Map<String, String> edicion : ediciones) {
			String nombre = edicion.get("Nombre");
			String evento = eventos.stream()
					.filter(e -> e.get("Ref").equals(edicion.get("Evento")))
					.map(e -> e.get("Nombre"))
					.findFirst()
					.orElse(null);
			String sigla = edicion.get("Sigla");
			String ciudad = edicion.get("Ciudad");
			String pais = edicion.get("Pais");
			
			String organizador = users.stream()
					.filter(u -> u.get("Ref").equals(edicion.get("Organizador")))
					.map(o -> o.get("Nickname"))
					.findFirst()
					.orElse(null);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Date fechaInicio = Date.from(LocalDate.parse(edicion.get("FechaInicio"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(LocalDate.parse(edicion.get("FechaFin"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date fechaAlta = Date.from(LocalDate.parse(edicion.get("FechaAlta"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			
			eventoController.agregarEdicionAEvento(evento, organizador, new datatypes.DTEdicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais));
		}
		
		//Carga de tipos de registro
		List<Map<String, String>> tiposRegistro = allRecords.get(FileName.TIPOREGISTROS.label);
		for (Map<String, String> tipoRegistro : tiposRegistro) {
			String edicion = ediciones.stream().filter(e -> e.get("Ref").equals(tipoRegistro.get("EdicionEvento"))).findFirst().orElse(null).get("Nombre");
			
			String nombre = tipoRegistro.get("Nombre");
			String descripcion = tipoRegistro.get("Descripcion");
			float costo = Float.parseFloat(tipoRegistro.get("Costo"));
			int cupo = Integer.parseInt(tipoRegistro.get("Cupo"));
			
			edicionController.altaTipoRegistro(edicion, new datatypes.DTTipoRegistro(nombre, descripcion, costo, cupo));

		}
		
		//Carga de patrocinios
		
		List<Map<String, String>> patrocinios = allRecords.get(FileName.PATROCINIOS.label);
		for (Map<String, String> patrocinio : patrocinios) {
			System.out.println(patrocinio);
			String edicion = ediciones.stream().filter(e -> e.get("Ref").equals(patrocinio.get("EdicionEvento"))).findFirst().orElse(null).get("Nombre");
			String institucion = instituciones.stream().filter(i -> i.get("Ref").equals(patrocinio.get("Institucion"))).findFirst().orElse(null).get("Nombre");
			String codigo = patrocinio.get("codigoPatrocinio");
			int cantidadRegistros = Integer.parseInt(patrocinio.get("cantidadRegistros"));
			Map<String, String> tipoRegistroMap = tiposRegistro.stream().filter(t -> t.get("Ref").equals(patrocinio.get("TipoRegistro"))).findFirst().orElse(null);
			String nombreTR = tipoRegistroMap.get("Nombre");
			String descripcionTR = tipoRegistroMap.get("Descripcion");
			float costoTR = Float.parseFloat(tipoRegistroMap.get("Costo"));
			int cupoTR = Integer.parseInt(tipoRegistroMap.get("Cupo"));
			institucionController.crearPatrocinio(edicion,
					institucion, 
					new DTRegistrosOtorgados(
							new DTTipoRegistro(nombreTR, descripcionTR, costoTR, cupoTR),
							cantidadRegistros), codigo);
		}
		
		//Carga de registros
		List<Map<String, String>> registros = allRecords.get(FileName.REGISTROS.label);
		for (Map<String, String> registro : registros) {
			String asistente = users.stream()
					.filter(a -> a.get("Ref").equals(registro.get("Usuario")))
					.map(a -> a.get("Nickname"))
					.findFirst()
					.orElse(null);
			String tipoRegistro = tiposRegistro.stream()
					.filter(t -> t.get("Ref").equals(registro.get("TipoRegistro")))
					.map(t -> t.get("Nombre"))
					.findFirst()
					.orElse(null);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Date fechaRegistro = Date.from(LocalDate.parse(registro.get("FechaRegistro"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			

			Map<String, String> edicion = ediciones.stream()
					.filter(e -> e.get("Ref").equals(registro.get("EdicionEvento")))
					.findFirst()
					.orElse(null);
			
		    String evento = eventos.stream()
		    		.filter(e -> e.get("Ref").equals(edicion.get("Evento")))
		    		.map(e -> e.get("Nombre"))
		    		.findFirst()
		    		.orElse(null);
		    
		    String edicionNombre = edicion.get("Nombre");
		    
			eventoController.mostrarEdiciones(evento);
			
			edicionController.mostrarTiposDeRegistro(edicionNombre);
			
			if(!(edicionController.cupoDisponible(tipoRegistro))){
				throw new ValidationInputException("TestLoader: No se puede registrar al asistente " + asistente + " en la edicion " + edicionNombre + " con el tipo de registro " + tipoRegistro + ". No quedan cupos disponibles");
			}
			
			if(!edicionController.asistenteNoRegistrado(asistente)) {
				throw new ValidationInputException("TestLoader: No se puede registrar al asistente " + asistente + " en la edicion " + edicionNombre + " con el tipo de registro " + tipoRegistro + ". El asistente ya se encuentra registrado en la edicion");
			}
			
			edicionController.altaRegistroEdicionEvento(tipoRegistro, asistente, fechaRegistro);
			
			
		
    }}
    }
