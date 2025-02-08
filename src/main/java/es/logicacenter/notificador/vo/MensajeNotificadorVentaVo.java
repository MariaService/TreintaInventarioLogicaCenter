package es.logicacenter.notificador.vo;

import java.util.List;

import es.logicacenter.notificador.entity.Venta;

public class MensajeNotificadorVentaVo {
   
	private List<String> listEgresos;
	private double montoTotalTienda;
	private double sumaEgresoDia;
	private String concepto;
	private Integer tipoOperacion;
	private String nombreTienda;
	private Integer catTienda;
	
	// 
	private double totalVentaOtraTienda;
	private double totalGastoOtraTienda;

	
	
	
	public List<String> getListEgresos() {
		return listEgresos;
	}
	public void setListEgresos(List<String> listEgresos) {
		this.listEgresos = listEgresos;
	}
	public double getMontoTotalTienda() {
		return montoTotalTienda;
	}
	public void setMontoTotalTienda(double montoTotalTienda) {
		this.montoTotalTienda = montoTotalTienda;
	}
	public double getSumaEgresoDia() {
		return sumaEgresoDia;
	}
	public void setSumaEgresoDia(double sumaEgresoDia) {
		this.sumaEgresoDia = sumaEgresoDia;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public double getTotalVentaOtraTienda() {
		return totalVentaOtraTienda;
	}
	public void setTotalVentaOtraTienda(double totalVentaOtraTienda) {
		this.totalVentaOtraTienda = totalVentaOtraTienda;
	}
	public double getTotalGastoOtraTienda() {
		return totalGastoOtraTienda;
	}
	public void setTotalGastoOtraTienda(double totalGastoOtraTienda) {
		this.totalGastoOtraTienda = totalGastoOtraTienda;
	}
	
	public Integer getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	
	public String getNombreTienda() {
		return nombreTienda;
	}
	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}
	
	
	
	public Integer getCatTienda() {
		return catTienda;
	}
	public void setCatTienda(Integer catTienda) {
		this.catTienda = catTienda;
	}
	@Override
	public String toString() {
		return "MensajeNotificadorVentaVo [listEgresos=" + listEgresos + ", montoTotalTienda=" + montoTotalTienda
				+ ", sumaEgresoDia=" + sumaEgresoDia + ", concepto=" + concepto + ", tipoOperacion=" + tipoOperacion
				+ ", nombreTienda=" + nombreTienda + ", catTienda=" + catTienda + ", totalVentaOtraTienda="
				+ totalVentaOtraTienda + ", totalGastoOtraTienda=" + totalGastoOtraTienda + "]";
	}
	
	
	
	
	
	
	


	

	
	
	
	
}
