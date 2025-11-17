// package da.obligatorio.peajes.controlador;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Scope;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;

// import da.obligatorio.peajes.ConexionNavegador;
// import da.obligatorio.peajes.Respuesta;
// import da.obligatorio.peajes.dto.NombreDTO;
// import da.obligatorio.peajes.modelo.Bonificacion;
// import da.obligatorio.peajes.modelo.Fachada;
// import da.obligatorio.peajes.modelo.Puesto;

// @RestController
// @Scope("session")
// public class ControladorBonificaciones {
//      private final ConexionNavegador conexionNavegador; 
     
//     public ControladorBonificaciones(@Autowired ConexionNavegador conexionNavegador) {
//         this.conexionNavegador = conexionNavegador;
//     }

//     @PostMapping("/vistaConectada")
// 	public List<Respuesta> inicializarVista() {
// 		return puestos();
// 	}

//     private List<Respuesta> puestos() {
//         List<NombreDTO> puestosDto = new ArrayList<NombreDTO>();
//         List<Puesto> puestos = Fachada.getInstancia().getPuestosPeaje();
//         for(Puesto p: puestos) {
//              puestosDto.add(new NombreDTO(p.getNombre()));
//         }

//         return Respuesta.lista(new Respuesta("puestos", puestosDto)); 
           
//     }

//     private List<Respuesta> bonificaciones() {
//         List<NombreDTO> bonificacionesDto = new ArrayList<NombreDTO>();
//         List<Bonificacion> bonificaciones = Fachada.getInstancia().getBonificaciones();
//         for(Bonificacion b: bonificaciones) {
//              bonificacionesDto.add(new NombreDTO(b.getNombre()));
//         }

//         return Respuesta.lista(new Respuesta("bonificaciones", bonificacionesDto)); 
           
//     }






// }
