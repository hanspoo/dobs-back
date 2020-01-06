package dolar.services;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dolar.models.DolarObservado;
import dolar.repos.RepoDolarObservado;

@Service
public class ServicioAlmacenarDolar {

	@Autowired
	RepoDolarObservado repo;

	public void actualizar(Map<LocalDate, Double> map) {

		map.entrySet().forEach(e -> {
			LocalDate key = e.getKey();
			Double value = e.getValue();
			agregarParidad(key, value);
		});

	}

	private void agregarParidad(LocalDate fecha, Double paridad) {
		DolarObservado data = repo.findByFecha(fecha);
		if (data == null)
			repo.save(new DolarObservado(fecha, paridad));

	}

}
