package es.trapasoft.student.service;

import es.trapasoft.student.entity.Venta;

public interface VentaService {

	
	Venta saveVenta( Venta venta);
	
	int countVentaPorfolio(String folio);
}
