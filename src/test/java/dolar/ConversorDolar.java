package dolar;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConversorDolar {

	private List<Double[]> filas;

	public ConversorDolar() throws IOException {
		String file = ParseaArchivoTest.class.getResource("banco-central.html").getFile();

		Document doc = Jsoup.parse(new File(file), "UTF-8");
		Element tbody = doc.select("table#gr > tbody").first();
		Elements children = tbody.children();

		filas = new ArrayList<>();
		for (Element tr : children.subList(1, children.size())) {

			Double[] textFromChildren = textFromChildren(tr);
			filas.add(textFromChildren);

		}

		filas.stream().forEach(f -> System.out.println(join(f)));

	}

	private String join(Double[] f) {
		List<Double> list = Arrays.asList(f);
		return list.stream().map(d -> d.toString()).collect(Collectors.joining("\t"));

	}

	public Double of(LocalDate of) {

		return filas.get(of.getDayOfMonth() - 1)[of.getMonthValue() - 1];

	}

	private Double[] textFromChildren(Element tr) {

		Double[] tds = new Double[12];
		int i = 0;
		Elements children = tr.children();
		for (Element src : children.subList(1, 13)) {
			String text = src.text();
			if (text != null && text.trim().length() > 0) {
				tds[i++] = Double.parseDouble(text.replace(",", "."));
			} else {
				tds[i++] = 0.0;
			}
		}

		return tds;

	}

}
