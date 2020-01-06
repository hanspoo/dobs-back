package dolar;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dolar.services.ServicioAlmacenarDolar;
import dolar.services.ServicioParidadBancoCentral;
import dolar.services.ServicioParidadDolarObservado;

@SpringBootTest
class DolarOservadoApplicationTests {

	@Autowired
	ServicioAlmacenarDolar s2 = new ServicioAlmacenarDolar();

	@Test
	void contextLoads() {
	}

	@Test
	void ejecutaServicio() throws JsonGenerationException, JsonMappingException, IOException {
		ServicioParidadDolarObservado sParidad = new ServicioParidadBancoCentral();
		Map<LocalDate, Double> map = sParidad.recuperarDatos();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File("/tmp/paridad-dolar.json"), map);
		
		s2.actualizar(map);
		
		map.entrySet().stream().forEach((e) -> System.out.println(e.getKey() + "=" + e.getValue()));
	}

	@Test
	@Disabled
	void deserializaMap() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<LocalDate, Double> map = objectMapper.readValue(new File("/tmp/paridad-dolar.json"), HashMap.class);

//		ServicioAlmacenarDolar s2 = new ServicioAlmacenarDolar();
//		s2.actualizar(map);
//		
		map.entrySet().stream().forEach((e) -> System.out.println(e.getKey() + "=" + e.getValue()));
	}

	@Test
	@Disabled
	void inserteEnRepo() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<LocalDate, Double> map = objectMapper.readValue(new File("/tmp/paridad-dolar.json"), HashMap.class);

		ServicioAlmacenarDolar s2 = new ServicioAlmacenarDolar();
		s2.actualizar(map);

	}

}
