package es.logicacenter.notificador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.logicacenter.notificador.entity.Venta;

public interface VentaRepository  extends JpaRepository<Venta, Long>{

	@Query("SELECT COUNT(v) FROM Venta v WHERE v.folio LIKE :folio")
    int contarVentasPorFolio(@Param("folio") String folio);
	
}
