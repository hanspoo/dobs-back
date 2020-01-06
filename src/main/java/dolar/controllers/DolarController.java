package dolar.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dolar.models.DolarObservado;
import dolar.repos.RepoDolarObservado;

@RestController
public class DolarController {

	@Autowired
	RepoDolarObservado repo;

	@GetMapping(value = { "/dolares/{fecha}" })
	public ResponseEntity<DolarObservado> dolarObservado(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		LocalDate fec = fecha == null ? LocalDate.now() : fecha;
		DolarObservado dolar = repo.findByFecha(fec);
		if (dolar == null)
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(dolar);
	}

}
