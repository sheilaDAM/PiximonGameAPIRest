package com.piximongameAPI.Entidades;

public class ResponseStatus {
    public enum TipoCodigo {
        REGISTRADO(1), YA_EXITE(2), ERROR(3), LOGIN_CORRECTO(4),
        LOGIN_INCORRECTO(5), DIGIMONS_INSERTADOS(6), DIGIMONS_YA_EXISTEN(7), INSERT_OK(8);;
        private final int valor;
        TipoCodigo(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }

    private final TipoCodigo tipoCodigo;

    public ResponseStatus(TipoCodigo tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public TipoCodigo getTipoCodigo() {
        return tipoCodigo;
    }
}
