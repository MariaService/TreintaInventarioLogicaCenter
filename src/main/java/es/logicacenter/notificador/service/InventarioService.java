package es.logicacenter.notificador.service;

import es.logicacenter.notificador.entity.Inventario;

public interface InventarioService {

	
	Inventario saveVenta( Inventario inv);
	
	int countVentaPorfolio(String folio);
}
