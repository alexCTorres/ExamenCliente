package Examen.CujiAlex.Servidor.negocio;

import Examen.CujiAlex.Servidor.modelo.Cliente;

public interface GestionClienteOnRemoto {

	public boolean registrarPersona(Cliente cliente);

	public Cliente buscarCliente(String identificacion);
}
