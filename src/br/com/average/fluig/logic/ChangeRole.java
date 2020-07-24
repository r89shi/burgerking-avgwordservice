package br.com.average.fluig.logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

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


			String[] hiVersao = json.getHiVersao();
			String[] hiPublicacao = json.getHiPublicacao();
			String[] hiElaborador = json.getHiElaborador();
			String[] hiArea = json.getHiArea();
			String[] hiConsenso = json.getHiConsenso();
			String[] hiOperacional = json.getHiOperacional();
			
			String[] coVersao = json.getCoVersao();
			String[] coPublicacao = json.getCoPublicacao();
			String[] coMotivo = json.getCoMotivo();
			
			
			String place = json.getPlace();
			String processo = json.getProcesso();
			
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
							String[] placeHolder = {"tkCodigo","tkValidade","tkResp","tkPlace","tkProcess"};
							
							for ( int a = 0; a < placeHolder.length; a++ ) {
								if(runText != null && Pattern.compile(placeHolder[a], Pattern.CASE_INSENSITIVE).matcher(runText).find()) {
									if ( placeHolder[a] == "tkCodigo") {
										runText = codigo;
									} else if ( placeHolder[a] == "tkValidade") {
										runText = validade;
									} else if ( placeHolder[a] == "tkResp") {
										runText = area_resp;
									} else if ( placeHolder[a] == "tkPlace") {
										runText = place;
									} else if ( placeHolder[a] == "tkProcess") {
										runText = processo;
									}
									
//									System.out.println(runText);
									c.removeParagraph(0);
									// c.setText(runText);
									
									XWPFParagraph addParagraph = c.addParagraph();
									XWPFRun run = addParagraph.createRun();
									run.setFontSize(7);
									run.setBold(true);
									run.setFontFamily("Arial");
//									run.getCTR().getRPr().getRFonts().setHAnsi("Arial");
									
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
			run3.setText(" ");
			
			// Construcao da tabela de registros decorrentes
			XWPFTable table1 = xdoc.createTable(2,6);
			table1.setCellMargins(50, 0, 50, 0);
			addText(table1, 0, 0, "Arial", "FFFFFF", "HISTÓRICO DE CRIAÇÃO/REVISÃO", true, 7, "0033CC", "c");
			mergeLine(table1, 0, 6);
			table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(11200));
			addText(table1, 1, 0, "Arial", "000000", "VERSÃO", true, 6, "AEAAAA", "c");
			addText(table1, 1, 1, "Arial", "000000", "DATA DE PUBLICAÇÃO", true, 6, "AEAAAA", "c");
			addText(table1, 1, 2, "Arial", "000000", "ELABORADOR", true, 6, "AEAAAA", "c");
			addText(table1, 1, 3, "Arial", "000000", "APROVADOR FINAL - ÁREA", true, 6, "AEAAAA", "c");
			addText(table1, 1, 4, "Arial", "000000", "APROVADOR FINAL - CONSENSO", true, 6, "AEAAAA", "c");
			addText(table1, 1, 5, "Arial", "000000", "APROVADOR FINAL - INT. OPERACIONAL", true, 6, "AEAAAA", "c");
			
			for (int i = 0; i < hiVersao.length; i++) {
				String[] palavras = {
						hiVersao[i], hiPublicacao[i], hiElaborador[i], hiArea[i], hiConsenso[i], hiOperacional[i]
				};
				createLine(table1, palavras, "Arial", "000000", false, 7);
			}
			
			
			XWPFParagraph para4 = xdoc.createParagraph();
			XWPFRun run4 = para4.createRun();
			run4.setFontSize(16);
			run4.setBold(true);
			run4.setText(" ");
			
			XWPFTable table2 = xdoc.createTable(2,3);
			table2.setCellMargins(50, 0, 50, 0);
			addText(table2, 0, 0, "Arial", "FFFFFF", "CONTROLE DE REVISÃO", true, 7, "ED7D31", "c");
			mergeLine(table2, 0, 3);
			
			addText(table2, 1, 0, "Arial", "000000", "VERSÃO", true, 6, "AEAAAA", "c");
			addText(table2, 1, 1, "Arial", "000000", "DATA DE PUBLICAÇÃO", true, 6, "AEAAAA", "c");
			addText(table2, 1, 2, "Arial", "000000", "MOTIVO", true, 6, "AEAAAA", "c");
			table2.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));
			table2.getRow(1).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1800));
			table2.getRow(1).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(8650));
			
			for (int i = 0; i < coVersao.length; i++) {
				String[] palavras = {
						coVersao[i], coPublicacao[i], coMotivo[i]
				};
				createLine(table2, palavras, "Arial", "000000", false, 7);
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
	
	public void addText(XWPFTable table, int linha, int coluna, String fonte, String cor, String texto, Boolean bold, int fSize, String cellColor, String textAlign) {
		table.getRow(linha).getCell(coluna).removeParagraph(0);
		table.getRow(linha).getCell(coluna).setColor(cellColor);
		
		XWPFParagraph p1 = table.getRow(linha).getCell(coluna).addParagraph();
		if (textAlign == "c") {
			p1.setAlignment(ParagraphAlignment.CENTER);
		}
		
		XWPFRun r1 = p1.createRun();
		r1.setFontSize(fSize);
		r1.setBold(bold);
		r1.setColor(cor);
		r1.setFontFamily(fonte);
		
		r1.setText(texto);
	}
	
	public void createLine(XWPFTable table, String[] valores, String fonte, String cor, Boolean bold, int fSize) {
		XWPFTableRow line = table.createRow();
		for (int i = 0; i < valores.length; i++) {
			line.getCell(i).removeParagraph(0);
			XWPFParagraph p1 = line.getCell(i).addParagraph();
			p1.setAlignment(ParagraphAlignment.CENTER);
			
			XWPFRun r1 = p1.createRun();
			r1.setFontSize(fSize);
			r1.setBold(bold);
			r1.setColor(cor);
			r1.setFontFamily(fonte);
			
			r1.setText(valores[i]);
		}
	}
	
	public void mergeLine(XWPFTable table, int line, int span) {
		for (int i = 0; i < span; i++) {
			XWPFTableCell cell = table.getRow(line).getCell(i);
			if (i == 0) {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
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
