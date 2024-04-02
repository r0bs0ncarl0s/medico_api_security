package med.voll.api.infra.security;

public interface ICifradorDeSenha {

	String cifrarSenha(String senha);

	boolean validarSenhaCrifrada(String senhaCifrada, String senha);

}
