package br.com.average.fluig.logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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

import org.json.simple.JSONObject;

public class ChangeRole {
	public JSONObject changeWordContent(JsonStructure json) {
		JSONObject resp = new JSONObject();
		
		try {
			String caminhoword = json.getDocUrl();
			String caminho_salva = json.getCaminhoSalva();
			
			// Parametos fixos no formulario
			String assunto = json.getAssunto();
			String codigo = json.getCodigo();
			String validade = json.getValidade();
			String area_resp = json.getArea_resp();


			String[] hiVersao = json.getHiVersao();
			String[] hiPublicacao = json.getHiPublicacao();
			String[] hiGestao = json.getHiGestao();
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
			ZipSecureFile.setMinInflateRatio(0);
			
			// Cria o documento com o conteudo do word aberto
			XWPFDocument xdoc = new XWPFDocument( OPCPackage.open(arquivo));
			
			
			System.out.println("*====================== INICIO DA EXECUCAO ========================*");
			/*
			 * Altera o cabecalho quando for criacao
			 */
			
			// Verifica se eh revisao
			if ( tipo_sol == 1 ) {
				substitui_tokens(xdoc);
				removeHistoryTables(xdoc);
				
//				resp.put("code", "201");
//				resp.put("message", "Test 2 processing finished...");
//				
//				return resp;
			}
			
			for (XWPFHeader h : xdoc.getHeaderList()) {
				for (XWPFParagraph p : h.getListParagraph())  {
					List<XWPFRun> runs = p.getRuns();
					for ( XWPFRun run : runs ) {
						String runText = run.getText(run.getTextPosition());
						String placeHolder = "tkAssunto";
						
						if(placeHolder !="" && !placeHolder.isEmpty()) {
			                if(runText != null && Pattern.compile(placeHolder, Pattern.CASE_INSENSITIVE).matcher(runText).find()) {
			                    runText = assunto;
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
									
									c.removeParagraph(0);
									XWPFParagraph addParagraph = c.addParagraph();
									XWPFRun run = addParagraph.createRun();
									run.setFontSize(7);
									run.setBold(true);
									run.setFontFamily("Arial");
									run.setText(runText);
				                }
							}
						}
					}
				}
			}
			
			/**
			 * Tabela de ficha tecnica
			 */
			
			XWPFParagraph para3 = xdoc.createParagraph();
			XWPFRun run3 = para3.createRun();
			run3.setFontSize(16);
			run3.setBold(true);
//			run3.addBreak(BreakType.PAGE);
			run3.setText(" ");
			
			// Construcao da tabela de registros decorrentes
			XWPFTable table1 = xdoc.createTable(2,7);
			table1.setCellMargins(50, 0, 50, 0);
			addText(table1, 0, 0, "Arial", "FFFFFF", "HISTÓRICO DE CRIAÇÃO/REVISÃO", true, 8, "0033CC", "c");
			mergeLine(table1, 0, 7);
			table1.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(11200));
			addText(table1, 1, 0, "Arial", "000000", "VERSÃO", true, 8, "EEECE1", "c");
			addText(table1, 1, 1, "Arial", "000000", "DATA DE PUBLICAÇÃO", true, 8, "EEECE1", "c");
			addText(table1, 1, 2, "Arial", "000000", "ELABORADOR", true, 8, "EEECE1", "c");
			addText(table1, 1, 3, "Arial", "000000", "APROVADOR GESTÃO", true, 8, "EEECE1", "c");
			addText(table1, 1, 4, "Arial", "000000", "APROVADOR FINAL - ÁREA", true, 8, "EEECE1", "c");
			addText(table1, 1, 5, "Arial", "000000", "APROVADOR FINAL - CONSENSO", true, 8, "EEECE1", "c");
			addText(table1, 1, 6, "Arial", "000000", "APROVADOR FINAL - INT. OPERACIONAL", true, 8, "EEECE1", "c");
			
			for (int i = 0; i < hiVersao.length; i++) {
				String[] palavras = {
						hiVersao[i], hiPublicacao[i], hiElaborador[i], hiGestao[i], hiArea[i], hiConsenso[i], hiOperacional[i]
				};
				createLine(table1, palavras, "Arial", "000000", false, 8);
			}
			
			
			XWPFParagraph para4 = xdoc.createParagraph();
			XWPFRun run4 = para4.createRun();
			run4.setFontSize(16);
			run4.setBold(true);
			run4.setText(" ");
			
			XWPFTable table2 = xdoc.createTable(2,3);
			table2.setCellMargins(50, 0, 50, 0);
			addText(table2, 0, 0, "Arial", "FFFFFF", "CONTROLE DE REVISÃO", true, 8, "ED7D31", "c");
			mergeLine(table2, 0, 3);
			
			addText(table2, 1, 0, "Arial", "000000", "VERSÃO", true, 8, "EEECE1", "c");
			addText(table2, 1, 1, "Arial", "000000", "DATA DE PUBLICAÇÃO", true, 8, "EEECE1", "c");
			addText(table2, 1, 2, "Arial", "000000", "MOTIVO", true, 8, "EEECE1", "c");
			table2.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));
			table2.getRow(1).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1800));
			table2.getRow(1).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(8650));
			
			for (int i = 0; i < coVersao.length; i++) {
				String[] palavras = {
						coVersao[i], coPublicacao[i], coMotivo[i]
				};
				createLine(table2, palavras, "Arial", "000000", false, 8);
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
		catch(Exception e) {
			resp.put("code", "400");
			resp.put("message", "Erro ao alterar word.");
			resp.put("detail", e);
			return resp;
		}
		
		resp.put("code", "200");
		resp.put("message", "Word alterado com sucesso.");
		
		return resp;
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
	
	public void apar(String mens) {
		System.out.println(mens);
	}
	
	public void altera_data_revisao(XWPFDocument doc , String nova_data) {
		System.out.println("Entrou na altera data");
		for (XWPFHeader hh : doc.getHeaderList()) {
			for ( XWPFTable t : hh.getTables() ) {
				for ( XWPFTableRow r : t.getRows() ) {
					List<XWPFTableCell> ltc = r.getTableCells();
					ltc.get(3).removeParagraph(0);
					ltc.get(3).setText(nova_data);
				}
			}
		}
	}
	
	public void setTextCell(XWPFTableRow r, int pos, String text) {
		List<XWPFTableCell> ltc = r.getTableCells();
		
		ltc.get(pos).removeParagraph(0);
		XWPFParagraph addParagraph = ltc.get(pos).addParagraph();
		XWPFRun run = addParagraph.createRun();
		run.setFontSize(7);
		run.setBold(true);
		run.setFontFamily("Arial");
		run.setText(text);
	}
	
	public void substitui_tokens(XWPFDocument doc) {
		int a = 0;
		for (XWPFHeader hh : doc.getHeaderList()) {
			for ( XWPFTable t : hh.getTables() ) {
				for ( XWPFTableRow r : t.getRows() ) {
					if (a == 0) {
						headerLine(hh);
						setTextCell(r, 1, "tkCodigo");
						setTextCell(r, 3, "tkValidade");
						setTextCell(r, 5, "tkResp");
					} if ( a == 1) {
						setTextCell(r, 1, "tkPlace");
						setTextCell(r, 3, "tkProcess");
					}
					a++;
				}
			}
		}
	}
	
	public void headerLine(XWPFHeader h) {
		int a = 0;
		for (XWPFParagraph p : h.getListParagraph())  {
			List<XWPFRun> runs = p.getRuns();
			
			if (a == 1) {
				for ( XWPFRun run : runs ) {
					if ( a == 1 ) {
						run.setText("                       ", 0);
					} else if ( a == 2 ) {
						run.setText("tkAssunto", 0);
					} else {
						run.setText("", 0);
					}
					a++;
				}
			}
			a++;
		}
	}

	public void removeHistoryTables(XWPFDocument xdoc) {
//		String text1 = "TB01HCR - HISTÓRICO DE CRIAÇÃO/REVISÃO";
//		String text2 = "TB02CTR - CONTROLE DE REVISÃO";
		
		String text1 = "HISTÓRICO DE CRIAÇÃO/REVISÃO";
		String text2 = "CONTROLE DE REVISÃO";
		
		// Remove tables
		for (int a = (xdoc.getTables().size() - 1); a > 0 ; a--) {
			XWPFTable t1 = xdoc.getTableArray(a);
			String titulo = t1.getRow(0).getCell(0).getText();
			
			if( (titulo != null && Pattern.compile(text1, Pattern.CASE_INSENSITIVE).matcher(titulo).find()) || 
					(titulo != null && Pattern.compile(text2, Pattern.CASE_INSENSITIVE).matcher(titulo).find()) ) {
				
				for (int b = (xdoc.getBodyElements().size() - 1); b > 0; b--) {
					if ( xdoc.getBodyElements().get(b) == xdoc.getTableArray(a) ) {
						xdoc.removeBodyElement(b);
					}
				}
			}
		}
	}
}
