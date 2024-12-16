package es.logicacenter.notificador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.logicacenter.notificador.entity.Inventario;
import es.logicacenter.notificador.entity.Venta;
import es.logicacenter.notificador.repository.InventarioRepository;
import es.logicacenter.notificador.repository.VentaRepository;
@Service 
public class InventarioServiceImp  implements InventarioService  {

	@Autowired
	private InventarioRepository  inventarioRepository;

	@Override
	public Inventario saveVenta(Inventario inv) {
		// TODO Auto-generated method stub
		return inventarioRepository.save(inv);
	}

	@Override
	public int countVentaPorfolio(String folio) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

}
