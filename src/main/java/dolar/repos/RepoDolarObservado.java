package dolar.repos;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dolar.models.DolarObservado;

@Repository
public interface RepoDolarObservado extends JpaRepository<DolarObservado, Long> {
	public DolarObservado findByFecha(LocalDate fecha);

}
