package es.logicacenter.notificador.Enum;

public enum EnumOperacionTienda {
	OPERACION_VENTA(1),
	OPERACION_GASTO(2);

    private final int valor;

    // Constructor para asignar el valor entero
    private EnumOperacionTienda(int valor) {
        this.valor = valor;
    }

    // Método para obtener el valor entero
    public int getValor() {
        return valor;
    }
}
