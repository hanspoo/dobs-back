package dolar.services;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.codeborne.selenide.WebDriverRunner;
import com.google.common.collect.Lists;

public class ServicioParidadBancoCentral implements ServicioParidadDolarObservado {

	private static final int ANO_INICIO = 2013;
	final int FILAS = 31;
	final int COLUMNAS = 13;

	Map<LocalDate, Double> conversionesDolar = new TreeMap<>();

	static {
		System.setProperty("phantomjs.binary.path", "/home/hans/Descargas/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
		// Initiate PhantomJSDriver.
		WebDriver driver = new PhantomJSDriver();
		WebDriverRunner.setWebDriver(driver);
	}

	Optional<Double> extraeNumero(Element element) {

		String s = element.text().replaceAll(",", ".").replaceAll("[^0-9.]", "");

		Optional<Double> optDouble = Optional.empty();
		if (s.length() > 0)
			optDouble = Optional.of(Double.parseDouble(s));

		return optDouble;

	}

	void actualizarAno(int ano) {

		$(By.id("DrDwnFechas")).click();
		{
			WebElement dropdown = $(By.id("DrDwnFechas"));
			dropdown.findElement(By.xpath("//option[. = '" + ano + "']")).click();
		}

		String html = $(By.id("gr")).innerHtml();

		Document doc = Jsoup.parse("<table>" + html + "</table>");

		Elements celdas = doc.select("tbody tr td");

		double[][] tabla = new double[FILAS][COLUMNAS];
		List<List<Element>> filas = Lists.partition(celdas, 13);

		int numFila = 0;
		for (List<Element> fila : filas) {
			for (int columna = 1; columna < fila.size(); columna++) {

				Optional<Double> numeroOpcional = extraeNumero(fila.get(columna));
				if (numeroOpcional.isPresent())
					tabla[numFila][columna - 1] = numeroOpcional.get();
			}
			numFila++;

		}

		LocalDate ahora = LocalDate.now();

		LocalDate diaEnCurso = LocalDate.of(ano, 1, 1);
		LocalDate fechaLimite = ahora.getYear() == ano ? ahora : LocalDate.of(ano, 12, 31);

		while (diaEnCurso.compareTo(fechaLimite) <= 0) {

			int dia = diaEnCurso.getDayOfMonth() - 1;
			int mes = diaEnCurso.getMonth().getValue() - 1;

			double tipoDeCambio = tabla[dia][mes];

			agregarParidad(diaEnCurso, tipoDeCambio);

			diaEnCurso = diaEnCurso.plus(1, ChronoUnit.DAYS);

		}

	}

	void agregarParidad(LocalDate fecha, double tipoDeCambio) {

		BigDecimal bd = new BigDecimal(tipoDeCambio);
		bd.setScale(2, RoundingMode.CEILING);
		conversionesDolar.put(fecha, bd.doubleValue());

	}

	@Override
	public Map<LocalDate, Double> recuperarDatos() {

		open("http://si3.bcentral.cl/Indicadoressiete/secure/Indicadoresdiarios.aspx");

		String linkText = "hypLnk1_3";
		$(By.id(linkText)).click();

		LocalDate ahora = LocalDate.now();
		for (int ano = ANO_INICIO; ano <= ahora.getYear(); ano++)
			actualizarAno(ano);

		Set<Entry<LocalDate, Double>> entrySet = conversionesDolar.entrySet();
		double ultimoTipoCambioValido = 0;
		for (Entry<LocalDate, Double> entry : entrySet) {
			Double tipoCambio = entry.getValue();
			if (tipoCambio == 0.0) {
				entry.setValue(ultimoTipoCambioValido);
			} else {
				ultimoTipoCambioValido = tipoCambio;
			}
		}

		rellenarFechasNulasAlFinal();
		probarCargaCorrectaDelSevicio();

		return conversionesDolar;

	}

	void rellenarFechasNulasAlFinal() {

		Double ultimaParidadRegistrada = ultimaParidadRegistrada();
		LocalDate fecha = LocalDate.now();

		while (conversionesDolar.get(fecha) == null) {

			agregarParidad(fecha, ultimaParidadRegistrada);
			fecha = fecha.minus(1L, ChronoUnit.DAYS);

		}

	}

	Double ultimaParidadRegistrada() {
		Collection<Double> collection = conversionesDolar.values();
		List<Double> list = new ArrayList<>(collection);
		return list.get(list.size() - 1);
	}

	void probarCargaCorrectaDelSevicio() {

		ServicioParidadDolarObservado servicioParidad = new ServicioParidadBancoCentral();
		Double valorHoy = conversionesDolar.get(LocalDate.now());
		if (valorHoy == null)
			throw new IllegalStateException("El servicio de paridad banco central no cargó la fecha de hoy");

		LocalDate fechaHans = LocalDate.of(2014, 10, 20);
		Validate.isTrue(partesEnterasIguales(587.61, conversionesDolar.get(fechaHans)));

		LocalDate nacKat = LocalDate.of(2013, 7, 23);
		Validate.isTrue(partesEnterasIguales(503.05, conversionesDolar.get(nacKat)));

		LocalDate primeroEnero2015 = LocalDate.of(2015, 1, 1);
		Double ultimaParidadRegistradaEn2014 = 607.38;
		Validate.isTrue(partesEnterasIguales(ultimaParidadRegistradaEn2014, conversionesDolar.get(primeroEnero2015)));

	}

	boolean partesEnterasIguales(double d1, double d2) {

		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.intValue() == b2.intValue();
	}

	Map<LocalDate, Double> getConversionesdolar() {
		return conversionesDolar;
	}

}
