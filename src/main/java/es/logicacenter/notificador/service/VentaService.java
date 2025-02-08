package es.logicacenter.notificador.service;

import java.util.List;

import es.logicacenter.notificador.entity.Venta;

public interface VentaService {

	
	Venta saveVenta( Venta venta);
	
	int countVentaPorfolio(String folio);
	
	double SumaMonto( Integer idTienda, Integer tipoOperacion);
	
	List<String> listaEgresoPorTienda(Integer idTienda, Integer tipoOpercion);
}
