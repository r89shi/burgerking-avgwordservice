package br.com.average.fluig.json;

public class JsonStructure {
	private String docUrl;
	private String caminhoSalva;
	private String assunto;
	private String codigo;
	private String validade;
	private String area_resp;
	private String place;
	private String processo;
	
	private String[] hiVersao;
	private String[] hiPublicacao;
	private String[] hiElaborador;
	private String[] hiGestao;
	private String[] hiArea;
	private String[] hiConsenso;
	private String[] hiOperacional;
	private String[] coVersao;
	private String[] coPublicacao;
	private String[] coMotivo;
	
	private int tipo_sol;

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getCaminhoSalva() {
		return caminhoSalva;
	}

	public void setCaminhoSalva(String caminhoSalva) {
		this.caminhoSalva = caminhoSalva;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getArea_resp() {
		return area_resp;
	}

	public void setArea_resp(String area_resp) {
		this.area_resp = area_resp;
	}

	public int getTipo_sol() {
		return tipo_sol;
	}

	public void setTipo_sol(int tipo_sol) {
		this.tipo_sol = tipo_sol;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPlace() {
		return this.place;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getProcesso() {
		return this.processo;
	}
	
	
	
	
	public String[] getHiVersao() {
		return hiVersao;
	}

	public void setHiVersao(String[] hiVersao) {
		this.hiVersao = hiVersao;
	}
	
	public String[] getHiPublicacao() {
		return hiPublicacao;
	}

	public void setHiPublicacao(String[] hiPublicacao) {
		this.hiPublicacao = hiPublicacao;
	}
	
	public String[] getHiElaborador() {
		return hiElaborador;
	}

	public void setHiElaborador(String[] hiElaborador) {
		this.hiElaborador = hiElaborador;
	}
	
	public String[] getHiArea() {
		return hiArea;
	}
	
	public void setHiGestao(String[] hiGestao) {
		this.hiGestao = hiGestao;
	}
	
	public String[] getHiGestao() {
		return hiGestao;
	}

	public void setHiArea(String[] hiArea) {
		this.hiArea = hiArea;
	}
	
	public String[] getHiConsenso() {
		return hiConsenso;
	}

	public void setHiConsenso(String[] hiConsenso) {
		this.hiConsenso = hiConsenso;
	}
	
	public String[] getHiOperacional() {
		return hiOperacional;
	}

	public void setHiOperacional(String[] hiOperacional) {
		this.hiOperacional = hiOperacional;
	}
	
	public String[] getCoVersao() {
		return coVersao;
	}

	public void setCoVersao(String[] coVersao) {
		this.coVersao = coVersao;
	}
	
	public String[] getCoPublicacao() {
		return coPublicacao;
	}

	public void setCoPublicacao(String[] coPublicacao) {
		this.coPublicacao = coPublicacao;
	}
	
	public String[] getCoMotivo() {
		return coMotivo;
	}

	public void setCoMotivo(String[] coMotivo) {
		this.coMotivo = coMotivo;
	}
}
