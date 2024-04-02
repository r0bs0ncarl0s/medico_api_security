package med.voll.api.infra.security;

public interface CifradorDeSenha {

	String cifrarSenha(String senha);

	boolean validarSenhaCrifrada(String senhaCifrada, String senha);

}
