package ca.ntro;

public interface NtroApp {
	
	void registerModels(ModelRegistrar registrar);
	void registerMessages(MessageRegistrar registrar);
	void registerControllers(ControllerRegistrar registar);

}
