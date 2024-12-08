package es.logicacenter.notificador.service;

import es.logicacenter.notificador.entity.Venta;

public interface VentaService {

	
	Venta saveVenta( Venta venta);
	
	int countVentaPorfolio(String folio);
}
