package configuration;



public class Configuration {
	
	public static int category;
	public static int inicialYear;
	public static int finalYear;
	public static int windowOfPrediction;
	
		
	/*public static int OFFSET = 0;
	public static int ANO_MINIMO = 1992;
	public static int ANO_MAXIMO = 2010;
			
	public static int COLUNA_BASE = 0;
	
	public static String CATEGORIA = "hep-lat";
	public static String RAIZ_NOME = CATEGORIA + "-";
	
	public static String RANDOM_WALK = "rw";
	public static String MEDIA = "med";
	public static String MEDIA_MOVEL = "med-mov";
	public static String ALISAMENTO_EXPONENCIAL = "alis-exp";
	public static String HOLT = "holt";
	public static String REG_LINEAR = "reg-lin";
	public static String FORECASTER;
	
	public static String SUPERVISIONADA = "sup";
	public static String NAO_SUPERVISIONADA = "unsup";
	public static String ABORDAGEM;
	
	//public static Similaridade[] SIMILARIDADES = {new PreferentialAttachment(), new CommonNeighbors(), new AdamicAdar(), new Jaccard(), new Katz(0.05, 3)};
	public static SimilarityMetric[] SIMILARIDADES = {new PreferentialAttachment(), new CommonNeighbors(), new AdamicAdar(), new Jaccard()};
		
	private static String CAMINHO_REDES = "redes/" + CATEGORIA + "/";
	private static String CAMINHO_GRAFOS = "grafos/" + CATEGORIA + "/";
	//private static String CAMINHO_GRAFOS_JANELAS = "grafosJanelas/" + CATEGORIA + "/";
	private static String CAMINHO_SCORES = "scores/" + CATEGORIA + "/";
	//private static String CAMINHO_KATZ = "katz/" + CATEGORIA + "/";
	private static String CAMINHO_HISTOGRAMAS = "hist/" + CATEGORIA + "/";
	private static String CAMINHO_HISTOGRAMAS_ERROS_PREDICAO = "hist/" + CATEGORIA + "/erros-predicao/";
	private static String CAMINHO_GRAFICOS_PR = "graficos/" + CATEGORIA + "/pr/";
	private static String CAMINHO_GRAFICOS_ROC = "graficos/" + CATEGORIA + "/roc/";
	private static String CAMINHO_LOG = "log/";
	private static String CAMINHO_SERIES = "series/" + CATEGORIA + "/"; 
	private static String CAMINHO_SERIES_WEKA = "series/" + CATEGORIA + "/weka/"; 
	private static String CAMINHO_SCORES_PREDICAO_NAO_SUP = "scores/" + CATEGORIA + "/predicao/unsup/";
	private static String CAMINHO_SCORES_PREDICAO_SUP = "scores/" + CATEGORIA + "/predicao/sup/"; 
	private static String CAMINHO_GRAFICOS_PR_PREDICAO = "graficos/" + CATEGORIA + "/predicao/pr/";
	private static String CAMINHO_GRAFICOS_ROC_PREDICAO = "graficos/" + CATEGORIA + "/predicao/roc/";
	private static String CAMINHO_RESULTADOS = "resultados/" + CATEGORIA + "/";
	
	public static String montarFilenameRede(int ano){
		return CAMINHO_REDES + RAIZ_NOME + ano + ".xml";
	}
	
	public static String montarFilenameScores(){
		return CAMINHO_SCORES + RAIZ_NOME + "scores-" + JANELA + ".txt";
	}
	
	public static String montarFilenameGraficoPR(){
		return CAMINHO_GRAFICOS_PR + RAIZ_NOME + "pr-" + JANELA + ".png";
	}
	
	public static String montarFilenameGraficoROC(){
		return CAMINHO_GRAFICOS_ROC + RAIZ_NOME + "roc-" + JANELA + ".png";
	}
	
	public static String montarFilenameHistograma(){
		return CAMINHO_HISTOGRAMAS + RAIZ_NOME + "hist" + JANELA + ".png";
	}
	
	public static String montarFilenameTesteMatrix(){
		return CAMINHO_GRAFOS_JANELAS + RAIZ_NOME + "teste.matrix";
	}
	
	public static String montarFilenameJanelaMatrix(){
		return CAMINHO_GRAFOS_JANELAS + RAIZ_NOME + "grafo" + ".matrix";
	}
	
	public static String montarFilenameKatzMatrix(){
		return CAMINHO_KATZ + RAIZ_NOME + "katz.matrix";
	}
	
	public static String montarFilenameLog(){
		return CAMINHO_LOG + "log.txt";
	}
	
	public static String montarComandoKatz(){
		return "cmd /c c:\\Python27\\python python\\katz_script.py " + montarFilenameJanelaMatrix() + " " + montarFilenameKatzMatrix();
	}

	public static String montarFilenameSeries(String serieId, String metrica){
		return CAMINHO_SERIES + RAIZ_NOME + "series-" + metrica + "-" + serieId + "-" + JANELA + "[" + OFFSET + "].txt";
	}	
		
	public static String montarFilenameSeriesKatz(){
		return CAMINHO_SERIES + RAIZ_NOME + "series-katz-" + JANELA + "[" + OFFSET + "].txt";
	}
	
	public static String montarFilenameSeriesWeka(String serieId, String metrica){
		return CAMINHO_SERIES_WEKA + RAIZ_NOME + "series-" + metrica + "-" + serieId + "-" + JANELA + "[" + OFFSET + "].arff";
	}	
	
	public static String montarFilenameSeriesClasses(String serieId){
		return CAMINHO_SERIES + RAIZ_NOME + "series-classes-" + serieId + "-" + JANELA + "[" + OFFSET + "].txt";
	}
	
	public static String montarFilenameScoresPredicaoNaoSup(String serieId, String obs){
		return CAMINHO_SCORES_PREDICAO_NAO_SUP  + FORECASTER + "/" + RAIZ_NOME + "scores-predicao-" + serieId + "-" + obs + "-" + JANELA + "[" + OFFSET + "].txt";
	}
	
	public static String montarFilenameScoresPredicaoSup(String serieId, String obs){
		return CAMINHO_SCORES_PREDICAO_SUP  + FORECASTER + "/" + RAIZ_NOME + "scores-predicao-" + serieId + "-" + obs + "-" + JANELA + "[" + OFFSET + "].arff";
	}
	
	public static String montarFilenameScoresPredicaoHibridoSup(String serieId, String obs){
		return CAMINHO_SCORES_PREDICAO_SUP  + FORECASTER + "/" + RAIZ_NOME + "scores-predicao-hibrido-" + serieId + "-" + obs + "-" + JANELA + "[" + OFFSET + "].arff";
	}
	
	public static String montarFilenameScoresPredicaoSupMelhoresPreditores(String serieId){
		return CAMINHO_SCORES_PREDICAO_SUP  + "/" + RAIZ_NOME + "scores-predicao-" + serieId + "-melhores-preditores-" + JANELA + "[" + OFFSET + "].arff";
	}
	
	public static String montarFilenameScoresPredicaoHibridoSupMelhoresPreditores(String serieId){
		return CAMINHO_SCORES_PREDICAO_SUP  + "/" + RAIZ_NOME + "scores-predicao-hibrido-" + serieId + "-melhores-preditores-" + JANELA + "[" + OFFSET + "].arff";
	}
	
	public static String montarFilenameScoresPredicaoTradicionalNaoSup(String serieId){
		return CAMINHO_SCORES_PREDICAO_NAO_SUP + RAIZ_NOME + "scores-tradicional-" + serieId + "-" + JANELA + "[" + OFFSET + "].txt";
	}
	
	public static String montarFilenameScoresPredicaoTradicionalSup(String serieId){
		return CAMINHO_SCORES_PREDICAO_SUP + RAIZ_NOME + "scores-tradicional-" + serieId + "-" + JANELA + "[" + OFFSET + "].arff";
	}
	
	public static String montarFilenameGraficoPRPredicao(){
		return CAMINHO_GRAFICOS_PR_PREDICAO + RAIZ_NOME + "pr-" + JANELA + "[" + OFFSET + "].png";
	}
	
	public static String montarFilenameGraficoROCPredicao(){
		return CAMINHO_GRAFICOS_ROC_PREDICAO + RAIZ_NOME + "roc-" + JANELA + "[" + OFFSET + "].png";
	}
	
	public static String montarFilenameGraficoPRPredicaoTradicional(){
		return CAMINHO_GRAFICOS_PR_PREDICAO + RAIZ_NOME + "pr-trad-" + JANELA + "[" + OFFSET + "].png";
	}
	
	public static String montarFilenameGraficoROCPredicaoTradicional(){
		return CAMINHO_GRAFICOS_ROC_PREDICAO + RAIZ_NOME + "roc-trad-" + JANELA + "[" + OFFSET + "].png";
	}
	
	private static int anoInicial(){
		int anos = ANO_MAXIMO - ANO_MINIMO + 1;
		return ANO_MINIMO + (anos-JANELA)%(JANELA - OFFSET); 		
	}
	
	public static String montarTituloDoGraficoSerie(){
		return "Série [" + anoInicial() + " - " + (ANO_MAXIMO - JANELA + OFFSET) + "]["+(ANO_MAXIMO-JANELA+1)+" - " + ANO_MAXIMO + "] - Janela " + JANELA + "/" + OFFSET;
	}
	
	public static String montarTituloDoGraficoTradicional(){
		return "Tradicional [" + anoInicial() + " - " + (ANO_MAXIMO - JANELA + OFFSET) + "]["+(ANO_MAXIMO-JANELA+1)+" - " + ANO_MAXIMO + "] - Janela " + JANELA + "/" + OFFSET;
	}
	
	public static int periodos(){
		return (int) Math.ceil((ANO_MAXIMO - anoInicial() + 1 - JANELA)/(JANELA - OFFSET));
	}*/
	
}
