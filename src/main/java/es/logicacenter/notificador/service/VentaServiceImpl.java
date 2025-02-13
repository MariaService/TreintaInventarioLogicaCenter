package es.logicacenter.notificador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.logicacenter.notificador.entity.Venta;
import es.logicacenter.notificador.repository.VentaRepository;
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


	@Override
	public double SumaMonto(Integer idTienda, Integer tipoOpercion) {
		// TODO Auto-generated method stub
		return  ventaRepositori.SumaMontoTotal( idTienda, tipoOpercion);
	}


	@Override
	public List<String> listaEgresoPorTienda(Integer idTienda, Integer tipoOpercion) {
		// TODO Auto-generated method stub
		return ventaRepositori.listaGastoTienda(idTienda, tipoOpercion);
	}

}
