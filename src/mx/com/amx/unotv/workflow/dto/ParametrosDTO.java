package mx.com.amx.unotv.workflow.dto;

import java.io.Serializable;

public class ParametrosDTO implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	
	private String carpetaResources;
	private String pathFiles;
	private String nameHTML;
	private String basePaginaPlantilla;
	private String baseTheme;
	private String baseURL;
	private String basePagesPortal;
	private String pathShell;
	private String pathRemote;
	private String pathDetalle;
	private String dominio;
	private String ambiente;
	private String metaVideo;
	private String metaVideoSecureUrl;
	private String pathFilesTest;
	private String pathRemoteTest;
	private String baseURLTest;
	private String catalogoParametros;
	
	private String URL_WS_DATOS;
	private String URL_WS_VIDEO;
	private String URL_WS_PARAMETROS;
	private String URL_WS_AMP;
	private String URL_WS_FB;
	private String URL_WEBSERVER_AMP;
	private String URL_WEBSERVER_CSS_AMP;
	
	
	/**
	 * @return the carpetaResources
	 */
	public String getCarpetaResources() {
		return carpetaResources;
	}
	/**
	 * @param carpetaResources the carpetaResources to set
	 */
	public void setCarpetaResources(String carpetaResources) {
		this.carpetaResources = carpetaResources;
	}
	/**
	 * @return the pathFiles
	 */
	public String getPathFiles() {
		return pathFiles;
	}
	/**
	 * @param pathFiles the pathFiles to set
	 */
	public void setPathFiles(String pathFiles) {
		this.pathFiles = pathFiles;
	}
	/**
	 * @return the nameHTML
	 */
	public String getNameHTML() {
		return nameHTML;
	}
	/**
	 * @param nameHTML the nameHTML to set
	 */
	public void setNameHTML(String nameHTML) {
		this.nameHTML = nameHTML;
	}
	/**
	 * @return the basePaginaPlantilla
	 */
	public String getBasePaginaPlantilla() {
		return basePaginaPlantilla;
	}
	/**
	 * @param basePaginaPlantilla the basePaginaPlantilla to set
	 */
	public void setBasePaginaPlantilla(String basePaginaPlantilla) {
		this.basePaginaPlantilla = basePaginaPlantilla;
	}
	/**
	 * @return the baseTheme
	 */
	public String getBaseTheme() {
		return baseTheme;
	}
	/**
	 * @param baseTheme the baseTheme to set
	 */
	public void setBaseTheme(String baseTheme) {
		this.baseTheme = baseTheme;
	}
	/**
	 * @return the baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}
	/**
	 * @param baseURL the baseURL to set
	 */
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	/**
	 * @return the basePagesPortal
	 */
	public String getBasePagesPortal() {
		return basePagesPortal;
	}
	/**
	 * @param basePagesPortal the basePagesPortal to set
	 */
	public void setBasePagesPortal(String basePagesPortal) {
		this.basePagesPortal = basePagesPortal;
	}
	/**
	 * @return the pathShell
	 */
	public String getPathShell() {
		return pathShell;
	}
	/**
	 * @param pathShell the pathShell to set
	 */
	public void setPathShell(String pathShell) {
		this.pathShell = pathShell;
	}
	/**
	 * @return the pathRemote
	 */
	public String getPathRemote() {
		return pathRemote;
	}
	/**
	 * @param pathRemote the pathRemote to set
	 */
	public void setPathRemote(String pathRemote) {
		this.pathRemote = pathRemote;
	}
	/**
	 * @return the pathDetalle
	 */
	public String getPathDetalle() {
		return pathDetalle;
	}
	/**
	 * @param pathDetalle the pathDetalle to set
	 */
	public void setPathDetalle(String pathDetalle) {
		this.pathDetalle = pathDetalle;
	}
	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}
	/**
	 * @param dominio the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	/**
	 * @return the ambiente
	 */
	public String getAmbiente() {
		return ambiente;
	}
	/**
	 * @param ambiente the ambiente to set
	 */
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	/**
	 * @return the metaVideo
	 */
	public String getMetaVideo() {
		return metaVideo;
	}
	/**
	 * @param metaVideo the metaVideo to set
	 */
	public void setMetaVideo(String metaVideo) {
		this.metaVideo = metaVideo;
	}
	/**
	 * @return the metaVideoSecureUrl
	 */
	public String getMetaVideoSecureUrl() {
		return metaVideoSecureUrl;
	}
	/**
	 * @param metaVideoSecureUrl the metaVideoSecureUrl to set
	 */
	public void setMetaVideoSecureUrl(String metaVideoSecureUrl) {
		this.metaVideoSecureUrl = metaVideoSecureUrl;
	}
	/**
	 * @return the pathFilesTest
	 */
	public String getPathFilesTest() {
		return pathFilesTest;
	}
	/**
	 * @param pathFilesTest the pathFilesTest to set
	 */
	public void setPathFilesTest(String pathFilesTest) {
		this.pathFilesTest = pathFilesTest;
	}
	/**
	 * @return the pathRemoteTest
	 */
	public String getPathRemoteTest() {
		return pathRemoteTest;
	}
	/**
	 * @param pathRemoteTest the pathRemoteTest to set
	 */
	public void setPathRemoteTest(String pathRemoteTest) {
		this.pathRemoteTest = pathRemoteTest;
	}
	/**
	 * @return the baseURLTest
	 */
	public String getBaseURLTest() {
		return baseURLTest;
	}
	/**
	 * @param baseURLTest the baseURLTest to set
	 */
	public void setBaseURLTest(String baseURLTest) {
		this.baseURLTest = baseURLTest;
	}
	/**
	 * @return the uRL_WS_DATOS
	 */
	public String getURL_WS_DATOS() {
		return URL_WS_DATOS;
	}
	/**
	 * @param uRL_WS_DATOS the uRL_WS_DATOS to set
	 */
	public void setURL_WS_DATOS(String uRL_WS_DATOS) {
		URL_WS_DATOS = uRL_WS_DATOS;
	}
	/**
	 * @return the uRL_WS_VIDEO
	 */
	public String getURL_WS_VIDEO() {
		return URL_WS_VIDEO;
	}
	/**
	 * @param uRL_WS_VIDEO the uRL_WS_VIDEO to set
	 */
	public void setURL_WS_VIDEO(String uRL_WS_VIDEO) {
		URL_WS_VIDEO = uRL_WS_VIDEO;
	}
	/**
	 * @return the uRL_WS_PARAMETROS
	 */
	public String getURL_WS_PARAMETROS() {
		return URL_WS_PARAMETROS;
	}
	/**
	 * @param uRL_WS_PARAMETROS the uRL_WS_PARAMETROS to set
	 */
	public void setURL_WS_PARAMETROS(String uRL_WS_PARAMETROS) {
		URL_WS_PARAMETROS = uRL_WS_PARAMETROS;
	}
	/**
	 * @return the uRL_WS_AMP
	 */
	public String getURL_WS_AMP() {
		return URL_WS_AMP;
	}
	/**
	 * @param uRL_WS_AMP the uRL_WS_AMP to set
	 */
	public void setURL_WS_AMP(String uRL_WS_AMP) {
		URL_WS_AMP = uRL_WS_AMP;
	}
	/**
	 * @return the uRL_WS_FB
	 */
	public String getURL_WS_FB() {
		return URL_WS_FB;
	}
	/**
	 * @param uRL_WS_FB the uRL_WS_FB to set
	 */
	public void setURL_WS_FB(String uRL_WS_FB) {
		URL_WS_FB = uRL_WS_FB;
	}
	/**
	 * @return the uRL_WEBSERVER_AMP
	 */
	public String getURL_WEBSERVER_AMP() {
		return URL_WEBSERVER_AMP;
	}
	/**
	 * @param uRL_WEBSERVER_AMP the uRL_WEBSERVER_AMP to set
	 */
	public void setURL_WEBSERVER_AMP(String uRL_WEBSERVER_AMP) {
		URL_WEBSERVER_AMP = uRL_WEBSERVER_AMP;
	}
	/**
	 * @return the uRL_WEBSERVER_CSS_AMP
	 */
	public String getURL_WEBSERVER_CSS_AMP() {
		return URL_WEBSERVER_CSS_AMP;
	}
	/**
	 * @param uRL_WEBSERVER_CSS_AMP the uRL_WEBSERVER_CSS_AMP to set
	 */
	public void setURL_WEBSERVER_CSS_AMP(String uRL_WEBSERVER_CSS_AMP) {
		URL_WEBSERVER_CSS_AMP = uRL_WEBSERVER_CSS_AMP;
	}
	/**
	 * @return the catalogoParametros
	 */
	public String getCatalogoParametros() {
		return catalogoParametros;
	}
	/**
	 * @param catalogoParametros the catalogoParametros to set
	 */
	public void setCatalogoParametros(String catalogoParametros) {
		this.catalogoParametros = catalogoParametros;
	}
	
	

}
