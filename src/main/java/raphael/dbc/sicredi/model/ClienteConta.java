package raphael.dbc.sicredi.model;

import lombok.Data;

@Data
public class ClienteConta {

    private String agencia;

    private String conta;

    private Double saldo;

    private String status;
}
