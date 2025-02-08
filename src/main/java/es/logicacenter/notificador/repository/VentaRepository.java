package es.logicacenter.notificador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.logicacenter.notificador.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

	@Query("SELECT COUNT(v) FROM Venta v WHERE v.folio LIKE :folio")
	int contarVentasPorFolio(@Param("folio") String folio);

	@Query("SELECT COALESCE(SUM(v.monto), 0.0) AS sumaMonto " + "FROM Venta v "
			+ "WHERE FUNCTION('DATE', v.fechaHora) = CURRENT_DATE "
			+ "AND v.catTienda = :idTienda  AND v.tipoOperacion= :tipoOperacion  ")
	double SumaMontoTotal(@Param("idTienda") Integer idTienda, @Param("tipoOperacion") Integer tipoOperacion);

	
	
	@Query("SELECT v.mensajeNotificacion FROM Venta v WHERE FUNCTION('DATE', v.fechaHora) = CURRENT_DATE AND v.catTienda = :idTienda AND v.tipoOperacion = :tipoOperacion")
	List<String> listaGastoTienda(@Param("idTienda") Integer idTienda, @Param("tipoOperacion") Integer tipoOperacion);
}
