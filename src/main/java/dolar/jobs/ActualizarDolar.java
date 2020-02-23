package dolar.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dolar.services.ServicioActualizarDolar;
import dolar.services.ServicioActualizarDolarObservado;

@Component
public class ActualizarDolar {

	@Scheduled(cron = "0 0 7 * * *")
	public void ejecutar() {

		ServicioActualizarDolar s = new ServicioActualizarDolarObservado();
		s.actualizar();

	}

}
