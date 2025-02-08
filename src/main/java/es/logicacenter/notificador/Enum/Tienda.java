package es.logicacenter.notificador.Enum;

public enum Tienda {
	MORELOS(1),
    BRAVO(2);

    private final int valor;

    // Constructor para asignar el valor entero
    private Tienda(int valor) {
        this.valor = valor;
    }

    // Método para obtener el valor entero
    public int getValor() {
        return valor;
    }
}
