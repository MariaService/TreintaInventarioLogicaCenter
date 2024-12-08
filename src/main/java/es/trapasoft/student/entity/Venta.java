package es.trapasoft.student.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "venta")
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fechaVenta;
	private String tipo;
	private String Descripcion;
	private String folio;
	private double monto;
	private int isNotificacion;
	private int consecutivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getIsNotificacion() {
		return isNotificacion;
	}

	public void setIsNotificacion(int isNotificacion) {
		this.isNotificacion = isNotificacion;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", fechaVenta=" + fechaVenta + ", tipo=" + tipo + ", Descripcion=" + Descripcion
				+ ", folio=" + folio + ", monto=" + monto + ", isNotificacion=" + isNotificacion + ", consecutivo="
				+ consecutivo + "]";
	}

	
	
	// campos de bitacora

}
