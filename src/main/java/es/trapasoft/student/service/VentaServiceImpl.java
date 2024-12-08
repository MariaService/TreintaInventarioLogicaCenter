package es.trapasoft.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.trapasoft.student.entity.Venta;
import es.trapasoft.student.repository.VentaRepository;
@Service 
public class VentaServiceImpl  implements VentaService  {

	@Autowired
	private VentaRepository ventaRepositori;
	
	
	@Override
	public Venta saveVenta(Venta venta) {
		// TODO Auto-generated method stub
		return  ventaRepositori.save(venta);
	}


	@Override
	public int countVentaPorfolio(String folio) {
		// TODO Auto-generated method stub
		return  ventaRepositori.contarVentasPorFolio(folio);
	}

}
