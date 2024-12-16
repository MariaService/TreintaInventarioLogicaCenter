package es.logicacenter.notificador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.logicacenter.notificador.entity.Inventario;

public interface InventarioRepository  extends JpaRepository<Inventario, Long>{

//	@Query("SELECT COUNT(v) FROM Venta v WHERE v.folio LIKE :folio")
//    int contarVentasPorFolio(@Param("folio") String folio);
	
}
