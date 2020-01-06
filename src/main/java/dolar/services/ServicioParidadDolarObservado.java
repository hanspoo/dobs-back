package dolar.services;

import java.time.LocalDate;
import java.util.Map;

public interface ServicioParidadDolarObservado {

	Map<LocalDate, Double> recuperarDatos();


}
