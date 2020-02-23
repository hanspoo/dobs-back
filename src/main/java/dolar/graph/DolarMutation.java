package dolar.graph;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import dolar.models.DolarObservado;
import dolar.services.ServicioActualizarDolar;

@Component
public class DolarMutation implements GraphQLMutationResolver {

	@Autowired
	ServicioActualizarDolar s;

	public List<DolarObservado> actualizarDolares() {

		s.actualizar();
		return Collections.emptyList();

	}

}