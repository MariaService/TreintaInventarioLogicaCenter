package es.logicacenter.notificador.entity;

import java.util.Date;

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

	private Date fechaVenta;
	private String tipo;
	private String Descripcion;
	private String folio;
	private double monto;
	private int isNotificacion;
	private int consecutivo;
	private String fechaHora;
	private Integer tipoOperacion;
	private String mensajeNotificacion;

	private Integer catTienda;

	private String userId;
	private String storeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
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

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Integer getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	
	
	

	public Integer getCatTienda() {
		return catTienda;
	}

	public void setCatTienda(Integer catTienda) {
		this.catTienda = catTienda;
	}

	public String getMensajeNotificacion() {
		return mensajeNotificacion;
	}

	public void setMensajeNotificacion(String mensajeNotificacion) {
		this.mensajeNotificacion = mensajeNotificacion;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", fechaVenta=" + fechaVenta + ", tipo=" + tipo + ", Descripcion=" + Descripcion
				+ ", folio=" + folio + ", monto=" + monto + ", isNotificacion=" + isNotificacion + ", consecutivo="
				+ consecutivo + ", fechaHora=" + fechaHora + ", tipoOperacion=" + tipoOperacion
				+ ", mensajeNotificacion=" + mensajeNotificacion + ", catTienda=" + catTienda + ", userId=" + userId
				+ ", storeId=" + storeId + "]";
	}



}
