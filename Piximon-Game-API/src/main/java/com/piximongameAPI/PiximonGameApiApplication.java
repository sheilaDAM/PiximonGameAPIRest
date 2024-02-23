package com.piximongameAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PiximonGameApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiximonGameApiApplication.class, args);

	}
}

/*
@SpringBootApplication
public class PiximonGameApiApplication implements CommandLineRunner {

	@Autowired
	private RepositorioDigimon repositorioDigimon;


	public static void main(String[] args) {
		SpringApplication.run(PiximonGameApiApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		List<Digimon> digimonsEnBaseDeDatos = repositorioDigimon.findAll();
		if (digimonsEnBaseDeDatos.isEmpty()) {
			// Si está vacía, realizar la llamada a la API
			IAPIServiceDigimon apiService = RestClientDigimon.getApiServiceInstance();
			apiService.obtenerDigimons().enqueue(new Callback<List<Digimon>>() {
				@Override
				public void onResponse(retrofit2.Call<List<Digimon>> call, retrofit2.Response<List<Digimon>> response) {
					List<Digimon> digimons = response.body();
					System.out.println("Número de Digimons obtenidos: " + digimons.size());

					// Guardar en la base de datos
					repositorioDigimon.saveAll(digimons);
				}

				@Override
				public void onFailure(retrofit2.Call<List<Digimon>> call, Throwable throwable) {
					System.out.println("Error al recuperar los digimons");
				}
			});
		} else {

			System.out.println("Número de Digimons en la base de datos: " + digimonsEnBaseDeDatos.size());

			List<Digimon> digimons = digimonsEnBaseDeDatos;
		}

	}
}
 */


