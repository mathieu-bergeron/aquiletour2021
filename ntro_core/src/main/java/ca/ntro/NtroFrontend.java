package ca.ntro;

public interface NtroFrontend {
	
	void registerModels(ModelRegistrar registrar);
	void registerMessages(MessageRegistrar registrar);
	void registerRootController(RootControllerRegistrar registar);

}
