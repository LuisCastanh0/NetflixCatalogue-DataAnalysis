package Netflix;

import java.util.Arrays;

public class ProgramaNetflix {
	// Atributos
	private String id;
	private String titulo;
	private String tipoDePrograma; // Corrected variable name
	private String descricao;
	private float releaseYear; // Corrected variable name
	private String ageCertification;
	private float runtime;
	private String[] generos;
	private String[] productionCountries;
	private float temporadas;
	private String imdbId; // Corrected variable name
	private float imdbScore; // Corrected variable name
	private float imdbVotes; // Corrected variable name
	private float tmdbPopularity;
	private float tmdbScore;

	// Construtor
	public ProgramaNetflix(String id, String titulo, String tipoDePrograma, String descricao, float releaseYear,
			String ageCertification, float runtime, String[] generos, String[] productionCountries, float temporadas,
			String imdbId, float imdbScore, float imdbVotes, float tmdbPopularity, float tmdbScore) {
		this.id = id;
		this.titulo = titulo;
		this.tipoDePrograma = tipoDePrograma;
		this.descricao = descricao;
		this.releaseYear = releaseYear;
		this.ageCertification = ageCertification;
		this.runtime = runtime;
		this.generos = generos;
		this.productionCountries = productionCountries;
		this.temporadas = temporadas;
		this.imdbId = imdbId;
		this.imdbScore = imdbScore;
		this.imdbVotes = imdbVotes;
		this.tmdbPopularity = tmdbPopularity;
		this.tmdbScore = tmdbScore;
	}

	// Métodos Getter
	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getTipoDePrograma() {
		return tipoDePrograma;
	}

	public String getDescricao() {
		return descricao;
	}

	public float getReleaseYear() {
		return releaseYear;
	}

	public String getAgeCertification() {
		return ageCertification;
	}

	public float getRuntime() {
		return runtime;
	}

	public String[] getGeneros() {
		return generos;
	}

	public String[] getProductionCountries() {
		return productionCountries;
	}

	public float getTemporadas() {
		return temporadas;
	}

	public String getImdbId() {
		return imdbId;
	}

	public float getImdbScore() {
		return imdbScore;
	}

	public float getImdbVotes() {
		return imdbVotes;
	}

	public float getTmdbPopularity() {
		return tmdbPopularity;
	}

	public float getTmdbScore() {
		return tmdbScore;
	}

	// Métodos Setter
	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setTipoDePrograma(String tipoDePrograma) {
		this.tipoDePrograma = tipoDePrograma;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setAgeCertification(String ageCertification) {
		this.ageCertification = ageCertification;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public void setGeneros(String[] generos) {
		this.generos = generos;
	}

	public void setProductionCountries(String[] productionCountries) {
		this.productionCountries = productionCountries;
	}

	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public void setImdbScore(float imdbScore) {
		this.imdbScore = imdbScore;
	}

	public void setImdbVotes(float imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public void setTmdbPopularity(float tmdbPopularity) {
		this.tmdbPopularity = tmdbPopularity;
	}

	public void setTmdbScore(float tmdbScore) {
		this.tmdbScore = tmdbScore;
	}
	
    @Override
    public String toString() {
        return  "ID: " + id + '\n' +
                "Título: " + titulo + '\n' +
                "Tipo de Programa: " + tipoDePrograma + '\n' +
                "Descrição: " + descricao + "\n" +
                "Ano de lançamento: " + releaseYear + "\n" +
                "Classificação etária: " + ageCertification + '\n' +
                "Duração: " + runtime + "\n" +
                "Gêneros: " + Arrays.toString(generos) + "\n" +
                "Países de produção: " + Arrays.toString(productionCountries) + "\n" + 
                "Número de Temporadas: " + temporadas + "\n" + 
                "Id do IMDB: " + imdbId + "\n" +
                "Pontuação do IMDB " + imdbScore + "\n" + 
                "Votos do IMDB: " + imdbVotes + "\n" + 
                "Popularidade TMDB: " + tmdbPopularity + "\n" + 
                "Pontuação TMDB: " + tmdbScore + "\n"; }
}
