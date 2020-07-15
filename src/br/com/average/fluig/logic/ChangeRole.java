package br.com.average.fluig.logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;

import br.com.average.fluig.json.JsonStructure;


public class ChangeRole
{
	public String changeWordContent(JsonStructure json)
	{
		try
		{
			String caminhoword = json.getDocUrl();
			String caminho_salva = json.getCaminhoSalva();
			
			// Parametos fixos no formulario
			String assunto = json.getAssunto();
			String codigo = json.getCodigo();
			String validade = json.getValidade();
			String area_resp = json.getArea_resp();
			String nom_ela = json.getNom_ela();
			String car_ela = json.getCar_ela();
			String dat_ale = json.getDat_ale();
			String nom_apr = json.getNom_apr();
			String car_apr = json.getCar_apr();
			String dat_apr = json.getDat_apr();
			
			// Dados da tabela de revisao
			String[] revisao = json.getRevisao();
			String[] data = json.getData();
			String[] descricao = json.getDescricao();
			
			// Dados da tabela de registros decorrentes
			String[] nom_arq = json.getNom_arq();
			String[] loc_arm = json.getLoc_arm();
			String[] qem_ace = json.getQem_ace();
			String[] qal_inf = json.getQal_inf();
			String[] per_ret = json.getPer_ret();
			String[] aps_exp = json.getAps_exp();
			
			int tipo_sol = json.getTipo_sol();
			
			URL objURL = new URL(caminhoword);
			InputStream arquivo = objURL.openStream();
			
			// Cria o documento com o conteudo do word aberto
			XWPFDocument xdoc = new XWPFDocument( OPCPackage.open(arquivo));
			
			
			System.out.println("*====================== INICIO DA EXECUCAO ========================*");
			/*
			 * Altera o cabecalho quando for criacao
			 */
			for (XWPFHeader h : xdoc.getHeaderList()) {
				for (XWPFParagraph p : h.getListParagraph())  {
					List<XWPFRun> runs = p.getRuns();
					for ( XWPFRun run : runs ) {
						String runText = run.getText(run.getTextPosition());
						String placeHolder = "tkAssunto";
						if(placeHolder !="" && !placeHolder.isEmpty()) {
			                if(runText != null && Pattern.compile(placeHolder, Pattern.CASE_INSENSITIVE).matcher(runText).find()) {
			                    runText = assunto;
//			                    System.out.println(runText);
					            run.setText(runText, 0);
			                }
			            }
					}
				}
			}
			
			for (XWPFHeader hh : xdoc.getHeaderList()) {
				for ( XWPFTable t : hh.getTables() ) {
					for ( XWPFTableRow r : t.getRows() ) {
						for ( XWPFTableCell c : r.getTableCells() ) {
							
							String runText = c.getText();
							String[] placeHolder = {"tkCodigo","tkValidade","tkResp"};
							
							for ( int a = 0; a < placeHolder.length; a++ ) {
								if(runText != null && Pattern.compile(placeHolder[a], Pattern.CASE_INSENSITIVE).matcher(runText).find()) {
									if ( placeHolder[a] == "tkCodigo") {
										runText = codigo;
									} else if ( placeHolder[a] == "tkValidade") {
										runText = validade;
									} else if ( placeHolder[a] == "tkResp") {
										runText = area_resp;
									}
									
									System.out.println(runText);
									c.removeParagraph(0);
									// c.setText(runText);
									
									XWPFParagraph addParagraph = c.addParagraph();
									XWPFRun run = addParagraph.createRun();
									run.setFontFamily("arial");
									run.setFontSize(7);
									run.setBold(true);
									run.setText(runText);
				                }
							}
						}
					}
				}
			}
			
			// Verifica se eh revisao
			if ( tipo_sol == 1 )
			{
				// remove a tabela
				altera_conteudo(xdoc);
				// Altera a data
				altera_data_revisao(xdoc, validade);
			}
			
			/**
			 * Tabela de ficha tecnica
			 */
			
			XWPFParagraph para3 = xdoc.createParagraph();
			XWPFRun run3 = para3.createRun();
			run3.setFontSize(16);
			run3.setBold(true);
			run3.addBreak(BreakType.PAGE);
			run3.setText("FICHA TÉCNICA");
			
			XWPFParagraph para4 = xdoc.createParagraph();
			XWPFRun run4 = para4.createRun();
			
			run4.setFontSize(14);
			run4.setText("Registros decorrentes do padrão");
			
			// Construcao da tabela de registros decorrentes
			XWPFTable td = xdoc.createTable(1,6);
			
			for ( int a = 0 ; a < nom_arq.length; a++ )
			{
				XWPFTableRow rd = null;
				
				if ( a == 0 )
				{
					rd = td.getRow(a);
					rd.getCell(0).setText("NOME DO ARQUIVO");
					rd.getCell(1).setText("LOCAL DE ARMAZENAMENTO");
					rd.getCell(2).setText("QUEM ACESSA");
					rd.getCell(3).setText("QUAL INFORM. PESQUISAR");
					rd.getCell(4).setText("PERIDO DE RETENCAO DO DOCTO");
					rd.getCell(5).setText("ACOES APOS EXPIRACAO");
					rd = td.createRow();
				}
				else
				{
					rd = td.createRow();
				}
				
				rd.getCell(0).setText(nom_arq[a]);
				rd.getCell(1).setText(loc_arm[a]);
				rd.getCell(2).setText(qem_ace[a]);
				rd.getCell(3).setText(qal_inf[a]);
				rd.getCell(4).setText(per_ret[a]);
				rd.getCell(5).setText(aps_exp[a]);
			}
			
			/**
			 * Change the header page
			 */
			XWPFParagraph para = xdoc.createParagraph();
			XWPFRun run1 = para.createRun();
			run1.setFontSize(14);
			run1.setText("CONTROLE DE ELABORAÇÃO / APROVAÇÃO");
			
			
			
			/**
			 * Criacao da tabela de aprovacao  - ffcc00
			 */
			// Criacao da table
			XWPFTable t = xdoc.createTable();
			
			// Linha 1
			XWPFTableRow r1 = t.getRow(0);
			r1.getCell(0).setText(" ");
			r1.addNewTableCell().setText("Elaborado por");
			r1.addNewTableCell().setText("Aprovado por");
			
			// Linha 2
			XWPFTableRow r2 = t.createRow();
			r2.getCell(0).setText("Nome");
			r2.getCell(1).setText(nom_ela);
			r2.getCell(2).setText(nom_apr);

			// Linha 3
			XWPFTableRow r3 = t.createRow();
			r3.getCell(0).setText("Cargo");
			r3.getCell(1).setText(car_ela);
			r3.getCell(2).setText(car_apr);

			// Linha 4
			XWPFTableRow r4 = t.createRow();
			r4.getCell(0).setText("Data");
			r4.getCell(1).setText(dat_ale);
			r4.getCell(2).setText(dat_apr);
			// Fim da tabela de aprovacao
			
			
			XWPFParagraph para2 = xdoc.createParagraph();
			XWPFRun run2 = para2.createRun();
//			run1.setFontFamily("calibri");
			run2.setFontSize(14);
			run2.setText("CONTROLE DE REVISÃO");
			
			XWPFTable tb_rev = xdoc.createTable(1,3);
			
			for ( int a = 0 ; a < revisao.length; a++ )
			{
				XWPFTableRow rr = null;
				if ( a == 0 )
				{
					rr = tb_rev.getRow(a);
					rr.getCell(0).setText("REVISAO");
					rr.getCell(1).setText("DATA");
					rr.getCell(2).setText("DESCRICAO");
					rr = tb_rev.createRow();
				}
				else
				{
					rr = tb_rev.createRow();
				}
				
				rr.getCell(0).setText(revisao[a]);
				rr.getCell(1).setText(data[a]);
				rr.getCell(2).setText(descricao[a]);
			}
			
			System.out.println("*====================== FIM DA EXECUCAO ========================*");
			
			// Cria uma saida
			FileOutputStream fos = new FileOutputStream(new File(caminho_salva));
			// Escreve no output
			xdoc.write(fos);
			// Fecha o arquivo
			fos.flush();
			fos.close();
			
			System.out.println("*============================================================== caminho de gravacao");
			System.out.println(caminho_salva);
		}
		catch(Exception e)
		{
			return "Ops, aconteceu um erro : " + e;
		}
		
		return "Word alterado";
	}
	
	
	/**
	 * altera_conteudo
	 * 
	 * Remove a tabela e os paragrafos
	 * 
	 * @param doc
	 */
	public void altera_conteudo(XWPFDocument doc)
	{
		boolean confere = true;
		int cont = 0;
		
		List<XWPFParagraph> pa = doc.getParagraphs();
		List<XWPFTable> tables = doc.getTables();
		
		while(confere)
		{
			int pas = pa.size() - cont - 1;
			int tbs = tables.size() - cont - 1;
			
			// Chama a funcao para apagar linha
			d_para(pa.get(pas), doc);
			
			// Loop para apagar a tabela ate a 3 posicao
			if ( cont != 4 )
				d_table(tables.get(tbs), doc);
			
			// Stop do loop
			if ( cont == 4 )
			{
				confere = false;
				apar("Estouro no cont");
			}
			cont++;
		}
	}
	
	
	
	
	public void d_para(XWPFParagraph p, XWPFDocument xdoc)
	{
		int pPos = xdoc.getPosOfParagraph(p);
		CTDocument1 ctb = xdoc.getDocument();
		CTBody ctb1 = ctb.getBody();
		ctb1.removeP(pPos);
	}
	
	
	public void d_table(XWPFTable t, XWPFDocument xdoc)
	{
		int pPos = xdoc.getPosOfTable(t);
		CTDocument1 ctb = xdoc.getDocument();
		CTBody ctb1 = ctb.getBody();
		ctb1.removeTbl(pPos);
	}
	
	
	
	public void apar(String mens)
	{
		System.out.println(mens);
	}
	
	
	/**
	 * validade
	 * 
	 * Altera o valor da data quando for revisao
	 * 
	 * @param doc XWPFDocument - input do documento word
	 * @param nova_data String - nova data =
	 */
	public void altera_data_revisao(XWPFDocument doc , String nova_data)
	{
		System.out.println("Entrou na altera data");
		for (XWPFHeader hh : doc.getHeaderList())
		{
			for ( XWPFTable t : hh.getTables() )
			{
				for ( XWPFTableRow r : t.getRows() )
				{
					List<XWPFTableCell> ltc = r.getTableCells();
					ltc.get(3).removeParagraph(0);
					ltc.get(3).setText(nova_data);
				}
			}
		}
	}
}
