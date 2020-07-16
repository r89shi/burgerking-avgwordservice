package br.com.average.fluig.json;

public class JsonStructure {
	private String docUrl;
	private String caminhoSalva;
	private String assunto;
	private String codigo;
	private String validade;
	private String area_resp;
	private String nom_ela;
	private String car_ela;
	private String dat_ale;
	private String nom_apr;
	private String car_apr;
	private String dat_apr;
	
	private String[] revisao;
	private String[] data;
	private String[] descricao;
	
	private String[] nom_arq;
	private String[] loc_arm;
	private String[] qem_ace;
	private String[] qal_inf;
	private String[] per_ret;
	private String[] aps_exp;
	
	private String place;
	private String processo;
	
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

	public String getNom_ela() {
		return nom_ela;
	}

	public void setNom_ela(String nom_ela) {
		this.nom_ela = nom_ela;
	}

	public String getCar_ela() {
		return car_ela;
	}

	public void setCar_ela(String car_ela) {
		this.car_ela = car_ela;
	}

	public String getDat_ale() {
		return dat_ale;
	}

	public void setDat_ale(String dat_ale) {
		this.dat_ale = dat_ale;
	}

	public String getNom_apr() {
		return nom_apr;
	}

	public void setNom_apr(String nom_apr) {
		this.nom_apr = nom_apr;
	}

	public String getCar_apr() {
		return car_apr;
	}

	public void setCar_apr(String car_apr) {
		this.car_apr = car_apr;
	}

	public String getDat_apr() {
		return dat_apr;
	}

	public void setDat_apr(String dat_apr) {
		this.dat_apr = dat_apr;
	}

	public String[] getRevisao() {
		return revisao;
	}

	public void setRevisao(String[] revisao) {
		this.revisao = revisao;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String[] getDescricao() {
		return descricao;
	}

	public void setDescricao(String[] descricao) {
		this.descricao = descricao;
	}

	public String[] getNom_arq() {
		return nom_arq;
	}

	public void setNom_arq(String[] nom_arq) {
		this.nom_arq = nom_arq;
	}

	public String[] getLoc_arm() {
		return loc_arm;
	}

	public void setLoc_arm(String[] loc_arm) {
		this.loc_arm = loc_arm;
	}

	public String[] getQem_ace() {
		return qem_ace;
	}

	public void setQem_ace(String[] qem_ace) {
		this.qem_ace = qem_ace;
	}

	public String[] getQal_inf() {
		return qal_inf;
	}

	public void setQal_inf(String[] qal_inf) {
		this.qal_inf = qal_inf;
	}

	public String[] getPer_ret() {
		return per_ret;
	}

	public void setPer_ret(String[] per_ret) {
		this.per_ret = per_ret;
	}

	public String[] getAps_exp() {
		return aps_exp;
	}

	public void setAps_exp(String[] aps_exp) {
		this.aps_exp = aps_exp;
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
}
