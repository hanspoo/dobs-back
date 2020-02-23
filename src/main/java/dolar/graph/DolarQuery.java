package dolar.graph;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import dolar.models.DolarObservado;
import dolar.repos.RepoDolarObservado;

@Component
public class DolarQuery implements GraphQLQueryResolver {

	@Autowired
	private RepoDolarObservado repo;

	public List<DolarObservado> getDolares(Integer ano) {

		List<DolarObservado> findAll = repo.findAll(Sort.by(Sort.Direction.ASC, "fecha"));
		if (ano != null)
			return findAll.stream().filter(d -> d.fecha.getYear() == ano).collect(Collectors.toList());

		return findAll;

	}

	public DolarObservado dolarHoy() {

		return repo.findByFecha(LocalDate.now());

	}

}