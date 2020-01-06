package dolar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParseaArchivoTest {

//	@Test
//	void contextLoads() throws IOException {
//		List<String[]> filas = cargarAchivo();
//
//		for (String[] strings : filas) {
//			System.out.println(join(strings));
//
//		}
//
//	}

	@Test
	public void dosEnero() throws IOException {
		ConversorDolar conversor = new ConversorDolar();
		assertThat(conversor.of(LocalDate.of(2019, 1, 2)), is(694.77));
		assertThat(conversor.of(LocalDate.of(2019, 1, 3)), is(697.09));
	}

	@Test
	public void diciembre() throws IOException {
		ConversorDolar conversor = new ConversorDolar();
		assertThat(conversor.of(LocalDate.of(2019, 12, 30)), is(744.62));
	}

}
