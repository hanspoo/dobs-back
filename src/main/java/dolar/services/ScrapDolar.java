package dolar.services;

import java.time.LocalDate;
import java.util.Map;

public interface ScrapDolar {

	Map<LocalDate, Double> recuperarDatos(Integer desde);


}
