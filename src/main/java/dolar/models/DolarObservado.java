package dolar.models;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "fecha" }) })
public class DolarObservado extends Model {

	@Basic(optional = false)
	public LocalDate fecha;

	@Basic(optional = false)
	public Double paridad;

	public DolarObservado(LocalDate fecha, Double paridad) {
		super();
		this.fecha = fecha;
		this.paridad = paridad;
	}

	public DolarObservado() {
		super();
	}

}
